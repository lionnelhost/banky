package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.JourFerierAsserts.*;
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
import sn.modeltech.banky.domain.JourFerier;
import sn.modeltech.banky.repository.JourFerierRepository;
import sn.modeltech.banky.service.dto.JourFerierDTO;
import sn.modeltech.banky.service.mapper.JourFerierMapper;

/**
 * Integration tests for the {@link JourFerierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JourFerierResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/jour-feriers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idJourFerie}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JourFerierRepository jourFerierRepository;

    @Autowired
    private JourFerierMapper jourFerierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJourFerierMockMvc;

    private JourFerier jourFerier;

    private JourFerier insertedJourFerier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourFerier createEntity() {
        return new JourFerier().idJourFerie(UUID.randomUUID().toString()).libelle(DEFAULT_LIBELLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourFerier createUpdatedEntity() {
        return new JourFerier().idJourFerie(UUID.randomUUID().toString()).libelle(UPDATED_LIBELLE);
    }

    @BeforeEach
    public void initTest() {
        jourFerier = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedJourFerier != null) {
            jourFerierRepository.delete(insertedJourFerier);
            insertedJourFerier = null;
        }
    }

    @Test
    @Transactional
    void createJourFerier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the JourFerier
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(jourFerier);
        var returnedJourFerierDTO = om.readValue(
            restJourFerierMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jourFerierDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JourFerierDTO.class
        );

        // Validate the JourFerier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedJourFerier = jourFerierMapper.toEntity(returnedJourFerierDTO);
        assertJourFerierUpdatableFieldsEquals(returnedJourFerier, getPersistedJourFerier(returnedJourFerier));

        insertedJourFerier = returnedJourFerier;
    }

    @Test
    @Transactional
    void createJourFerierWithExistingId() throws Exception {
        // Create the JourFerier with an existing ID
        insertedJourFerier = jourFerierRepository.saveAndFlush(jourFerier);
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(jourFerier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourFerierMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jourFerierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JourFerier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJourFeriers() throws Exception {
        // Initialize the database
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());
        insertedJourFerier = jourFerierRepository.saveAndFlush(jourFerier);

        // Get all the jourFerierList
        restJourFerierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idJourFerie,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idJourFerie").value(hasItem(jourFerier.getIdJourFerie())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getJourFerier() throws Exception {
        // Initialize the database
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());
        insertedJourFerier = jourFerierRepository.saveAndFlush(jourFerier);

        // Get the jourFerier
        restJourFerierMockMvc
            .perform(get(ENTITY_API_URL_ID, jourFerier.getIdJourFerie()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idJourFerie").value(jourFerier.getIdJourFerie()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingJourFerier() throws Exception {
        // Get the jourFerier
        restJourFerierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJourFerier() throws Exception {
        // Initialize the database
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());
        insertedJourFerier = jourFerierRepository.saveAndFlush(jourFerier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jourFerier
        JourFerier updatedJourFerier = jourFerierRepository.findById(jourFerier.getIdJourFerie()).orElseThrow();
        // Disconnect from session so that the updates on updatedJourFerier are not directly saved in db
        em.detach(updatedJourFerier);
        updatedJourFerier.libelle(UPDATED_LIBELLE);
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(updatedJourFerier);

        restJourFerierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jourFerierDTO.getIdJourFerie())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jourFerierDTO))
            )
            .andExpect(status().isOk());

        // Validate the JourFerier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJourFerierToMatchAllProperties(updatedJourFerier);
    }

    @Test
    @Transactional
    void putNonExistingJourFerier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());

        // Create the JourFerier
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(jourFerier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourFerierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jourFerierDTO.getIdJourFerie())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jourFerierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourFerier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJourFerier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());

        // Create the JourFerier
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(jourFerier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourFerierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jourFerierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourFerier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJourFerier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());

        // Create the JourFerier
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(jourFerier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourFerierMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jourFerierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourFerier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJourFerierWithPatch() throws Exception {
        // Initialize the database
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());
        insertedJourFerier = jourFerierRepository.saveAndFlush(jourFerier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jourFerier using partial update
        JourFerier partialUpdatedJourFerier = new JourFerier();
        partialUpdatedJourFerier.setIdJourFerie(jourFerier.getIdJourFerie());

        partialUpdatedJourFerier.libelle(UPDATED_LIBELLE);

        restJourFerierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourFerier.getIdJourFerie())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJourFerier))
            )
            .andExpect(status().isOk());

        // Validate the JourFerier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJourFerierUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedJourFerier, jourFerier),
            getPersistedJourFerier(jourFerier)
        );
    }

    @Test
    @Transactional
    void fullUpdateJourFerierWithPatch() throws Exception {
        // Initialize the database
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());
        insertedJourFerier = jourFerierRepository.saveAndFlush(jourFerier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jourFerier using partial update
        JourFerier partialUpdatedJourFerier = new JourFerier();
        partialUpdatedJourFerier.setIdJourFerie(jourFerier.getIdJourFerie());

        partialUpdatedJourFerier.libelle(UPDATED_LIBELLE);

        restJourFerierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourFerier.getIdJourFerie())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJourFerier))
            )
            .andExpect(status().isOk());

        // Validate the JourFerier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJourFerierUpdatableFieldsEquals(partialUpdatedJourFerier, getPersistedJourFerier(partialUpdatedJourFerier));
    }

    @Test
    @Transactional
    void patchNonExistingJourFerier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());

        // Create the JourFerier
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(jourFerier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourFerierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jourFerierDTO.getIdJourFerie())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jourFerierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourFerier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJourFerier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());

        // Create the JourFerier
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(jourFerier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourFerierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jourFerierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourFerier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJourFerier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());

        // Create the JourFerier
        JourFerierDTO jourFerierDTO = jourFerierMapper.toDto(jourFerier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourFerierMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jourFerierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourFerier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJourFerier() throws Exception {
        // Initialize the database
        jourFerier.setIdJourFerie(UUID.randomUUID().toString());
        insertedJourFerier = jourFerierRepository.saveAndFlush(jourFerier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jourFerier
        restJourFerierMockMvc
            .perform(delete(ENTITY_API_URL_ID, jourFerier.getIdJourFerie()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jourFerierRepository.count();
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

    protected JourFerier getPersistedJourFerier(JourFerier jourFerier) {
        return jourFerierRepository.findById(jourFerier.getIdJourFerie()).orElseThrow();
    }

    protected void assertPersistedJourFerierToMatchAllProperties(JourFerier expectedJourFerier) {
        assertJourFerierAllPropertiesEquals(expectedJourFerier, getPersistedJourFerier(expectedJourFerier));
    }

    protected void assertPersistedJourFerierToMatchUpdatableProperties(JourFerier expectedJourFerier) {
        assertJourFerierAllUpdatablePropertiesEquals(expectedJourFerier, getPersistedJourFerier(expectedJourFerier));
    }
}
