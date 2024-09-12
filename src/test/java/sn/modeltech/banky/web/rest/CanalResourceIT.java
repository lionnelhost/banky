package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.CanalAsserts.*;
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
import sn.modeltech.banky.domain.Canal;
import sn.modeltech.banky.repository.CanalRepository;
import sn.modeltech.banky.service.dto.CanalDTO;
import sn.modeltech.banky.service.mapper.CanalMapper;

/**
 * Integration tests for the {@link CanalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CanalResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/canals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idCanal}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private CanalMapper canalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCanalMockMvc;

    private Canal canal;

    private Canal insertedCanal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Canal createEntity() {
        return new Canal().idCanal(UUID.randomUUID().toString()).libelle(DEFAULT_LIBELLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Canal createUpdatedEntity() {
        return new Canal().idCanal(UUID.randomUUID().toString()).libelle(UPDATED_LIBELLE);
    }

    @BeforeEach
    public void initTest() {
        canal = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCanal != null) {
            canalRepository.delete(insertedCanal);
            insertedCanal = null;
        }
    }

    @Test
    @Transactional
    void createCanal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Canal
        CanalDTO canalDTO = canalMapper.toDto(canal);
        var returnedCanalDTO = om.readValue(
            restCanalMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(canalDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CanalDTO.class
        );

        // Validate the Canal in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCanal = canalMapper.toEntity(returnedCanalDTO);
        assertCanalUpdatableFieldsEquals(returnedCanal, getPersistedCanal(returnedCanal));

        insertedCanal = returnedCanal;
    }

    @Test
    @Transactional
    void createCanalWithExistingId() throws Exception {
        // Create the Canal with an existing ID
        insertedCanal = canalRepository.saveAndFlush(canal);
        CanalDTO canalDTO = canalMapper.toDto(canal);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCanalMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(canalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Canal in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCanals() throws Exception {
        // Initialize the database
        canal.setIdCanal(UUID.randomUUID().toString());
        insertedCanal = canalRepository.saveAndFlush(canal);

        // Get all the canalList
        restCanalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idCanal,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idCanal").value(hasItem(canal.getIdCanal())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getCanal() throws Exception {
        // Initialize the database
        canal.setIdCanal(UUID.randomUUID().toString());
        insertedCanal = canalRepository.saveAndFlush(canal);

        // Get the canal
        restCanalMockMvc
            .perform(get(ENTITY_API_URL_ID, canal.getIdCanal()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idCanal").value(canal.getIdCanal()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingCanal() throws Exception {
        // Get the canal
        restCanalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCanal() throws Exception {
        // Initialize the database
        canal.setIdCanal(UUID.randomUUID().toString());
        insertedCanal = canalRepository.saveAndFlush(canal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the canal
        Canal updatedCanal = canalRepository.findById(canal.getIdCanal()).orElseThrow();
        // Disconnect from session so that the updates on updatedCanal are not directly saved in db
        em.detach(updatedCanal);
        updatedCanal.libelle(UPDATED_LIBELLE);
        CanalDTO canalDTO = canalMapper.toDto(updatedCanal);

        restCanalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, canalDTO.getIdCanal())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(canalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Canal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCanalToMatchAllProperties(updatedCanal);
    }

    @Test
    @Transactional
    void putNonExistingCanal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        canal.setIdCanal(UUID.randomUUID().toString());

        // Create the Canal
        CanalDTO canalDTO = canalMapper.toDto(canal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCanalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, canalDTO.getIdCanal())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(canalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Canal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCanal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        canal.setIdCanal(UUID.randomUUID().toString());

        // Create the Canal
        CanalDTO canalDTO = canalMapper.toDto(canal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCanalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(canalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Canal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCanal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        canal.setIdCanal(UUID.randomUUID().toString());

        // Create the Canal
        CanalDTO canalDTO = canalMapper.toDto(canal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCanalMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(canalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Canal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCanalWithPatch() throws Exception {
        // Initialize the database
        canal.setIdCanal(UUID.randomUUID().toString());
        insertedCanal = canalRepository.saveAndFlush(canal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the canal using partial update
        Canal partialUpdatedCanal = new Canal();
        partialUpdatedCanal.setIdCanal(canal.getIdCanal());

        partialUpdatedCanal.libelle(UPDATED_LIBELLE);

        restCanalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCanal.getIdCanal())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCanal))
            )
            .andExpect(status().isOk());

        // Validate the Canal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCanalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCanal, canal), getPersistedCanal(canal));
    }

    @Test
    @Transactional
    void fullUpdateCanalWithPatch() throws Exception {
        // Initialize the database
        canal.setIdCanal(UUID.randomUUID().toString());
        insertedCanal = canalRepository.saveAndFlush(canal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the canal using partial update
        Canal partialUpdatedCanal = new Canal();
        partialUpdatedCanal.setIdCanal(canal.getIdCanal());

        partialUpdatedCanal.libelle(UPDATED_LIBELLE);

        restCanalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCanal.getIdCanal())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCanal))
            )
            .andExpect(status().isOk());

        // Validate the Canal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCanalUpdatableFieldsEquals(partialUpdatedCanal, getPersistedCanal(partialUpdatedCanal));
    }

    @Test
    @Transactional
    void patchNonExistingCanal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        canal.setIdCanal(UUID.randomUUID().toString());

        // Create the Canal
        CanalDTO canalDTO = canalMapper.toDto(canal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCanalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, canalDTO.getIdCanal())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(canalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Canal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCanal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        canal.setIdCanal(UUID.randomUUID().toString());

        // Create the Canal
        CanalDTO canalDTO = canalMapper.toDto(canal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCanalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(canalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Canal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCanal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        canal.setIdCanal(UUID.randomUUID().toString());

        // Create the Canal
        CanalDTO canalDTO = canalMapper.toDto(canal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCanalMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(canalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Canal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCanal() throws Exception {
        // Initialize the database
        canal.setIdCanal(UUID.randomUUID().toString());
        insertedCanal = canalRepository.saveAndFlush(canal);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the canal
        restCanalMockMvc
            .perform(delete(ENTITY_API_URL_ID, canal.getIdCanal()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return canalRepository.count();
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

    protected Canal getPersistedCanal(Canal canal) {
        return canalRepository.findById(canal.getIdCanal()).orElseThrow();
    }

    protected void assertPersistedCanalToMatchAllProperties(Canal expectedCanal) {
        assertCanalAllPropertiesEquals(expectedCanal, getPersistedCanal(expectedCanal));
    }

    protected void assertPersistedCanalToMatchUpdatableProperties(Canal expectedCanal) {
        assertCanalAllUpdatablePropertiesEquals(expectedCanal, getPersistedCanal(expectedCanal));
    }
}
