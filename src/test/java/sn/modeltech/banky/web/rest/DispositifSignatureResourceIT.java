package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.DispositifSignatureAsserts.*;
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
import sn.modeltech.banky.domain.DispositifSignature;
import sn.modeltech.banky.repository.DispositifSignatureRepository;
import sn.modeltech.banky.service.dto.DispositifSignatureDTO;
import sn.modeltech.banky.service.mapper.DispositifSignatureMapper;

/**
 * Integration tests for the {@link DispositifSignatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DispositifSignatureResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dispositif-signatures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idDispositif}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DispositifSignatureRepository dispositifSignatureRepository;

    @Autowired
    private DispositifSignatureMapper dispositifSignatureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDispositifSignatureMockMvc;

    private DispositifSignature dispositifSignature;

    private DispositifSignature insertedDispositifSignature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispositifSignature createEntity() {
        return new DispositifSignature().idDispositif(UUID.randomUUID().toString()).libelle(DEFAULT_LIBELLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispositifSignature createUpdatedEntity() {
        return new DispositifSignature().idDispositif(UUID.randomUUID().toString()).libelle(UPDATED_LIBELLE);
    }

    @BeforeEach
    public void initTest() {
        dispositifSignature = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDispositifSignature != null) {
            dispositifSignatureRepository.delete(insertedDispositifSignature);
            insertedDispositifSignature = null;
        }
    }

    @Test
    @Transactional
    void createDispositifSignature() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DispositifSignature
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(dispositifSignature);
        var returnedDispositifSignatureDTO = om.readValue(
            restDispositifSignatureMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(dispositifSignatureDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DispositifSignatureDTO.class
        );

        // Validate the DispositifSignature in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDispositifSignature = dispositifSignatureMapper.toEntity(returnedDispositifSignatureDTO);
        assertDispositifSignatureUpdatableFieldsEquals(
            returnedDispositifSignature,
            getPersistedDispositifSignature(returnedDispositifSignature)
        );

        insertedDispositifSignature = returnedDispositifSignature;
    }

    @Test
    @Transactional
    void createDispositifSignatureWithExistingId() throws Exception {
        // Create the DispositifSignature with an existing ID
        insertedDispositifSignature = dispositifSignatureRepository.saveAndFlush(dispositifSignature);
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(dispositifSignature);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositifSignatureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSignatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSignature in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDispositifSignatures() throws Exception {
        // Initialize the database
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());
        insertedDispositifSignature = dispositifSignatureRepository.saveAndFlush(dispositifSignature);

        // Get all the dispositifSignatureList
        restDispositifSignatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idDispositif,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idDispositif").value(hasItem(dispositifSignature.getIdDispositif())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getDispositifSignature() throws Exception {
        // Initialize the database
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());
        insertedDispositifSignature = dispositifSignatureRepository.saveAndFlush(dispositifSignature);

        // Get the dispositifSignature
        restDispositifSignatureMockMvc
            .perform(get(ENTITY_API_URL_ID, dispositifSignature.getIdDispositif()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idDispositif").value(dispositifSignature.getIdDispositif()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingDispositifSignature() throws Exception {
        // Get the dispositifSignature
        restDispositifSignatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDispositifSignature() throws Exception {
        // Initialize the database
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());
        insertedDispositifSignature = dispositifSignatureRepository.saveAndFlush(dispositifSignature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositifSignature
        DispositifSignature updatedDispositifSignature = dispositifSignatureRepository
            .findById(dispositifSignature.getIdDispositif())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDispositifSignature are not directly saved in db
        em.detach(updatedDispositifSignature);
        updatedDispositifSignature.libelle(UPDATED_LIBELLE);
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(updatedDispositifSignature);

        restDispositifSignatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositifSignatureDTO.getIdDispositif())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSignatureDTO))
            )
            .andExpect(status().isOk());

        // Validate the DispositifSignature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDispositifSignatureToMatchAllProperties(updatedDispositifSignature);
    }

    @Test
    @Transactional
    void putNonExistingDispositifSignature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());

        // Create the DispositifSignature
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(dispositifSignature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositifSignatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositifSignatureDTO.getIdDispositif())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSignatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSignature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDispositifSignature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());

        // Create the DispositifSignature
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(dispositifSignature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositifSignatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSignatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSignature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDispositifSignature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());

        // Create the DispositifSignature
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(dispositifSignature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositifSignatureMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSignatureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DispositifSignature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDispositifSignatureWithPatch() throws Exception {
        // Initialize the database
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());
        insertedDispositifSignature = dispositifSignatureRepository.saveAndFlush(dispositifSignature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositifSignature using partial update
        DispositifSignature partialUpdatedDispositifSignature = new DispositifSignature();
        partialUpdatedDispositifSignature.setIdDispositif(dispositifSignature.getIdDispositif());

        restDispositifSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositifSignature.getIdDispositif())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDispositifSignature))
            )
            .andExpect(status().isOk());

        // Validate the DispositifSignature in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDispositifSignatureUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDispositifSignature, dispositifSignature),
            getPersistedDispositifSignature(dispositifSignature)
        );
    }

    @Test
    @Transactional
    void fullUpdateDispositifSignatureWithPatch() throws Exception {
        // Initialize the database
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());
        insertedDispositifSignature = dispositifSignatureRepository.saveAndFlush(dispositifSignature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositifSignature using partial update
        DispositifSignature partialUpdatedDispositifSignature = new DispositifSignature();
        partialUpdatedDispositifSignature.setIdDispositif(dispositifSignature.getIdDispositif());

        partialUpdatedDispositifSignature.libelle(UPDATED_LIBELLE);

        restDispositifSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositifSignature.getIdDispositif())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDispositifSignature))
            )
            .andExpect(status().isOk());

        // Validate the DispositifSignature in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDispositifSignatureUpdatableFieldsEquals(
            partialUpdatedDispositifSignature,
            getPersistedDispositifSignature(partialUpdatedDispositifSignature)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDispositifSignature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());

        // Create the DispositifSignature
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(dispositifSignature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositifSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dispositifSignatureDTO.getIdDispositif())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositifSignatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSignature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDispositifSignature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());

        // Create the DispositifSignature
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(dispositifSignature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositifSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositifSignatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSignature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDispositifSignature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());

        // Create the DispositifSignature
        DispositifSignatureDTO dispositifSignatureDTO = dispositifSignatureMapper.toDto(dispositifSignature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositifSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositifSignatureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DispositifSignature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDispositifSignature() throws Exception {
        // Initialize the database
        dispositifSignature.setIdDispositif(UUID.randomUUID().toString());
        insertedDispositifSignature = dispositifSignatureRepository.saveAndFlush(dispositifSignature);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dispositifSignature
        restDispositifSignatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, dispositifSignature.getIdDispositif()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dispositifSignatureRepository.count();
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

    protected DispositifSignature getPersistedDispositifSignature(DispositifSignature dispositifSignature) {
        return dispositifSignatureRepository.findById(dispositifSignature.getIdDispositif()).orElseThrow();
    }

    protected void assertPersistedDispositifSignatureToMatchAllProperties(DispositifSignature expectedDispositifSignature) {
        assertDispositifSignatureAllPropertiesEquals(
            expectedDispositifSignature,
            getPersistedDispositifSignature(expectedDispositifSignature)
        );
    }

    protected void assertPersistedDispositifSignatureToMatchUpdatableProperties(DispositifSignature expectedDispositifSignature) {
        assertDispositifSignatureAllUpdatablePropertiesEquals(
            expectedDispositifSignature,
            getPersistedDispositifSignature(expectedDispositifSignature)
        );
    }
}
