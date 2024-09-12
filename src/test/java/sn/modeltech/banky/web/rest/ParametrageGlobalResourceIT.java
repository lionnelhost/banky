package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.ParametrageGlobalAsserts.*;
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
import sn.modeltech.banky.domain.ParametrageGlobal;
import sn.modeltech.banky.repository.ParametrageGlobalRepository;
import sn.modeltech.banky.service.dto.ParametrageGlobalDTO;
import sn.modeltech.banky.service.mapper.ParametrageGlobalMapper;

/**
 * Integration tests for the {@link ParametrageGlobalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParametrageGlobalResourceIT {

    private static final String DEFAULT_CODE_PARAM = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_PARAM = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_PARAM = "BBBBBBBBBB";

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parametrage-globals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idParamGlobal}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParametrageGlobalRepository parametrageGlobalRepository;

    @Autowired
    private ParametrageGlobalMapper parametrageGlobalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametrageGlobalMockMvc;

    private ParametrageGlobal parametrageGlobal;

    private ParametrageGlobal insertedParametrageGlobal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParametrageGlobal createEntity() {
        return new ParametrageGlobal()
            .idParamGlobal(UUID.randomUUID().toString())
            .codeParam(DEFAULT_CODE_PARAM)
            .typeParam(DEFAULT_TYPE_PARAM)
            .valeur(DEFAULT_VALEUR);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParametrageGlobal createUpdatedEntity() {
        return new ParametrageGlobal()
            .idParamGlobal(UUID.randomUUID().toString())
            .codeParam(UPDATED_CODE_PARAM)
            .typeParam(UPDATED_TYPE_PARAM)
            .valeur(UPDATED_VALEUR);
    }

    @BeforeEach
    public void initTest() {
        parametrageGlobal = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedParametrageGlobal != null) {
            parametrageGlobalRepository.delete(insertedParametrageGlobal);
            insertedParametrageGlobal = null;
        }
    }

    @Test
    @Transactional
    void createParametrageGlobal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ParametrageGlobal
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(parametrageGlobal);
        var returnedParametrageGlobalDTO = om.readValue(
            restParametrageGlobalMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(parametrageGlobalDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ParametrageGlobalDTO.class
        );

        // Validate the ParametrageGlobal in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedParametrageGlobal = parametrageGlobalMapper.toEntity(returnedParametrageGlobalDTO);
        assertParametrageGlobalUpdatableFieldsEquals(returnedParametrageGlobal, getPersistedParametrageGlobal(returnedParametrageGlobal));

        insertedParametrageGlobal = returnedParametrageGlobal;
    }

    @Test
    @Transactional
    void createParametrageGlobalWithExistingId() throws Exception {
        // Create the ParametrageGlobal with an existing ID
        insertedParametrageGlobal = parametrageGlobalRepository.saveAndFlush(parametrageGlobal);
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(parametrageGlobal);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametrageGlobalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageGlobalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageGlobal in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParametrageGlobals() throws Exception {
        // Initialize the database
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());
        insertedParametrageGlobal = parametrageGlobalRepository.saveAndFlush(parametrageGlobal);

        // Get all the parametrageGlobalList
        restParametrageGlobalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idParamGlobal,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idParamGlobal").value(hasItem(parametrageGlobal.getIdParamGlobal())))
            .andExpect(jsonPath("$.[*].codeParam").value(hasItem(DEFAULT_CODE_PARAM)))
            .andExpect(jsonPath("$.[*].typeParam").value(hasItem(DEFAULT_TYPE_PARAM)))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)));
    }

    @Test
    @Transactional
    void getParametrageGlobal() throws Exception {
        // Initialize the database
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());
        insertedParametrageGlobal = parametrageGlobalRepository.saveAndFlush(parametrageGlobal);

        // Get the parametrageGlobal
        restParametrageGlobalMockMvc
            .perform(get(ENTITY_API_URL_ID, parametrageGlobal.getIdParamGlobal()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idParamGlobal").value(parametrageGlobal.getIdParamGlobal()))
            .andExpect(jsonPath("$.codeParam").value(DEFAULT_CODE_PARAM))
            .andExpect(jsonPath("$.typeParam").value(DEFAULT_TYPE_PARAM))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR));
    }

    @Test
    @Transactional
    void getNonExistingParametrageGlobal() throws Exception {
        // Get the parametrageGlobal
        restParametrageGlobalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParametrageGlobal() throws Exception {
        // Initialize the database
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());
        insertedParametrageGlobal = parametrageGlobalRepository.saveAndFlush(parametrageGlobal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parametrageGlobal
        ParametrageGlobal updatedParametrageGlobal = parametrageGlobalRepository
            .findById(parametrageGlobal.getIdParamGlobal())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedParametrageGlobal are not directly saved in db
        em.detach(updatedParametrageGlobal);
        updatedParametrageGlobal.codeParam(UPDATED_CODE_PARAM).typeParam(UPDATED_TYPE_PARAM).valeur(UPDATED_VALEUR);
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(updatedParametrageGlobal);

        restParametrageGlobalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parametrageGlobalDTO.getIdParamGlobal())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageGlobalDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParametrageGlobal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParametrageGlobalToMatchAllProperties(updatedParametrageGlobal);
    }

    @Test
    @Transactional
    void putNonExistingParametrageGlobal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());

        // Create the ParametrageGlobal
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(parametrageGlobal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametrageGlobalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parametrageGlobalDTO.getIdParamGlobal())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageGlobalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageGlobal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParametrageGlobal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());

        // Create the ParametrageGlobal
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(parametrageGlobal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametrageGlobalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageGlobalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageGlobal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParametrageGlobal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());

        // Create the ParametrageGlobal
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(parametrageGlobal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametrageGlobalMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parametrageGlobalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParametrageGlobal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParametrageGlobalWithPatch() throws Exception {
        // Initialize the database
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());
        insertedParametrageGlobal = parametrageGlobalRepository.saveAndFlush(parametrageGlobal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parametrageGlobal using partial update
        ParametrageGlobal partialUpdatedParametrageGlobal = new ParametrageGlobal();
        partialUpdatedParametrageGlobal.setIdParamGlobal(parametrageGlobal.getIdParamGlobal());

        partialUpdatedParametrageGlobal.valeur(UPDATED_VALEUR);

        restParametrageGlobalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParametrageGlobal.getIdParamGlobal())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParametrageGlobal))
            )
            .andExpect(status().isOk());

        // Validate the ParametrageGlobal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParametrageGlobalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParametrageGlobal, parametrageGlobal),
            getPersistedParametrageGlobal(parametrageGlobal)
        );
    }

    @Test
    @Transactional
    void fullUpdateParametrageGlobalWithPatch() throws Exception {
        // Initialize the database
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());
        insertedParametrageGlobal = parametrageGlobalRepository.saveAndFlush(parametrageGlobal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parametrageGlobal using partial update
        ParametrageGlobal partialUpdatedParametrageGlobal = new ParametrageGlobal();
        partialUpdatedParametrageGlobal.setIdParamGlobal(parametrageGlobal.getIdParamGlobal());

        partialUpdatedParametrageGlobal.codeParam(UPDATED_CODE_PARAM).typeParam(UPDATED_TYPE_PARAM).valeur(UPDATED_VALEUR);

        restParametrageGlobalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParametrageGlobal.getIdParamGlobal())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParametrageGlobal))
            )
            .andExpect(status().isOk());

        // Validate the ParametrageGlobal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParametrageGlobalUpdatableFieldsEquals(
            partialUpdatedParametrageGlobal,
            getPersistedParametrageGlobal(partialUpdatedParametrageGlobal)
        );
    }

    @Test
    @Transactional
    void patchNonExistingParametrageGlobal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());

        // Create the ParametrageGlobal
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(parametrageGlobal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametrageGlobalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parametrageGlobalDTO.getIdParamGlobal())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parametrageGlobalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageGlobal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParametrageGlobal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());

        // Create the ParametrageGlobal
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(parametrageGlobal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametrageGlobalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parametrageGlobalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageGlobal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParametrageGlobal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());

        // Create the ParametrageGlobal
        ParametrageGlobalDTO parametrageGlobalDTO = parametrageGlobalMapper.toDto(parametrageGlobal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametrageGlobalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parametrageGlobalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParametrageGlobal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParametrageGlobal() throws Exception {
        // Initialize the database
        parametrageGlobal.setIdParamGlobal(UUID.randomUUID().toString());
        insertedParametrageGlobal = parametrageGlobalRepository.saveAndFlush(parametrageGlobal);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parametrageGlobal
        restParametrageGlobalMockMvc
            .perform(delete(ENTITY_API_URL_ID, parametrageGlobal.getIdParamGlobal()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parametrageGlobalRepository.count();
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

    protected ParametrageGlobal getPersistedParametrageGlobal(ParametrageGlobal parametrageGlobal) {
        return parametrageGlobalRepository.findById(parametrageGlobal.getIdParamGlobal()).orElseThrow();
    }

    protected void assertPersistedParametrageGlobalToMatchAllProperties(ParametrageGlobal expectedParametrageGlobal) {
        assertParametrageGlobalAllPropertiesEquals(expectedParametrageGlobal, getPersistedParametrageGlobal(expectedParametrageGlobal));
    }

    protected void assertPersistedParametrageGlobalToMatchUpdatableProperties(ParametrageGlobal expectedParametrageGlobal) {
        assertParametrageGlobalAllUpdatablePropertiesEquals(
            expectedParametrageGlobal,
            getPersistedParametrageGlobal(expectedParametrageGlobal)
        );
    }
}
