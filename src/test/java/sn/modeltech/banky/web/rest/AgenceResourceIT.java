package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.AgenceAsserts.*;
import static sn.modeltech.banky.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.IntegrationTest;
import sn.modeltech.banky.domain.Agence;
import sn.modeltech.banky.repository.AgenceRepository;
import sn.modeltech.banky.service.dto.AgenceDTO;
import sn.modeltech.banky.service.mapper.AgenceMapper;

/**
 * Integration tests for the {@link AgenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgenceResourceIT {

    private static final String DEFAULT_CODE_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_AGENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idAgence}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private AgenceMapper agenceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgenceMockMvc;

    private Agence agence;

    private Agence insertedAgence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createEntity() {
        return new Agence().idAgence(UUID.randomUUID().toString()).codeAgence(DEFAULT_CODE_AGENCE).nomAgence(DEFAULT_NOM_AGENCE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createUpdatedEntity() {
        return new Agence().idAgence(UUID.randomUUID().toString()).codeAgence(UPDATED_CODE_AGENCE).nomAgence(UPDATED_NOM_AGENCE);
    }

    @BeforeEach
    public void initTest() {
        agence = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAgence != null) {
            agenceRepository.delete(insertedAgence);
            insertedAgence = null;
        }
    }

    @Test
    @Transactional
    void createAgence() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);
        var returnedAgenceDTO = om.readValue(
            restAgenceMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AgenceDTO.class
        );

        // Validate the Agence in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAgence = agenceMapper.toEntity(returnedAgenceDTO);
        assertAgenceUpdatableFieldsEquals(returnedAgence, getPersistedAgence(returnedAgence));

        insertedAgence = returnedAgence;
    }

    @Test
    @Transactional
    void createAgenceWithExistingId() throws Exception {
        // Create the Agence with an existing ID
        insertedAgence = agenceRepository.saveAndFlush(agence);
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgences() throws Exception {
        // Initialize the database
        agence.setIdAgence(UUID.randomUUID().toString());
        insertedAgence = agenceRepository.saveAndFlush(agence);

        // Get all the agenceList
        restAgenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idAgence,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idAgence").value(hasItem(agence.getIdAgence())))
            .andExpect(jsonPath("$.[*].codeAgence").value(hasItem(DEFAULT_CODE_AGENCE)))
            .andExpect(jsonPath("$.[*].nomAgence").value(hasItem(DEFAULT_NOM_AGENCE)));
    }

    @Test
    @Transactional
    void getAgence() throws Exception {
        // Initialize the database
        agence.setIdAgence(UUID.randomUUID().toString());
        insertedAgence = agenceRepository.saveAndFlush(agence);

        // Get the agence
        restAgenceMockMvc
            .perform(get(ENTITY_API_URL_ID, agence.getIdAgence()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idAgence").value(agence.getIdAgence()))
            .andExpect(jsonPath("$.codeAgence").value(DEFAULT_CODE_AGENCE))
            .andExpect(jsonPath("$.nomAgence").value(DEFAULT_NOM_AGENCE));
    }

    @Test
    @Transactional
    void getNonExistingAgence() throws Exception {
        // Get the agence
        restAgenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgence() throws Exception {
        // Initialize the database
        agence.setIdAgence(UUID.randomUUID().toString());
        insertedAgence = agenceRepository.saveAndFlush(agence);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agence
        Agence updatedAgence = agenceRepository.findById(agence.getIdAgence()).orElseThrow();
        // Disconnect from session so that the updates on updatedAgence are not directly saved in db
        em.detach(updatedAgence);
        updatedAgence.codeAgence(UPDATED_CODE_AGENCE).nomAgence(UPDATED_NOM_AGENCE);
        AgenceDTO agenceDTO = agenceMapper.toDto(updatedAgence);

        restAgenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agenceDTO.getIdAgence())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Agence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgenceToMatchAllProperties(updatedAgence);
    }

    @Test
    @Transactional
    void putNonExistingAgence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agence.setIdAgence(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agenceDTO.getIdAgence())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agence.setIdAgence(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agence.setIdAgence(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenceMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgenceWithPatch() throws Exception {
        // Initialize the database
        agence.setIdAgence(UUID.randomUUID().toString());
        insertedAgence = agenceRepository.saveAndFlush(agence);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agence using partial update
        Agence partialUpdatedAgence = new Agence();
        partialUpdatedAgence.setIdAgence(agence.getIdAgence());

        restAgenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgence.getIdAgence())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgence))
            )
            .andExpect(status().isOk());

        // Validate the Agence in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgenceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAgence, agence), getPersistedAgence(agence));
    }

    @Test
    @Transactional
    void fullUpdateAgenceWithPatch() throws Exception {
        // Initialize the database
        agence.setIdAgence(UUID.randomUUID().toString());
        insertedAgence = agenceRepository.saveAndFlush(agence);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agence using partial update
        Agence partialUpdatedAgence = new Agence();
        partialUpdatedAgence.setIdAgence(agence.getIdAgence());

        partialUpdatedAgence.codeAgence(UPDATED_CODE_AGENCE).nomAgence(UPDATED_NOM_AGENCE);

        restAgenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgence.getIdAgence())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgence))
            )
            .andExpect(status().isOk());

        // Validate the Agence in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgenceUpdatableFieldsEquals(partialUpdatedAgence, getPersistedAgence(partialUpdatedAgence));
    }

    @Test
    @Transactional
    void patchNonExistingAgence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agence.setIdAgence(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agenceDTO.getIdAgence())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agence.setIdAgence(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agence.setIdAgence(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenceMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgence() throws Exception {
        // Initialize the database
        agence.setIdAgence(UUID.randomUUID().toString());
        insertedAgence = agenceRepository.saveAndFlush(agence);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agence
        restAgenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, agence.getIdAgence()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agenceRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Agence getPersistedAgence(Agence agence) {
        return agenceRepository.findById(agence.getIdAgence()).orElseThrow();
    }

    protected void assertPersistedAgenceToMatchAllProperties(Agence expectedAgence) {
        assertAgenceAllPropertiesEquals(expectedAgence, getPersistedAgence(expectedAgence));
    }

    protected void assertPersistedAgenceToMatchUpdatableProperties(Agence expectedAgence) {
        assertAgenceAllUpdatablePropertiesEquals(expectedAgence, getPersistedAgence(expectedAgence));
    }
}
