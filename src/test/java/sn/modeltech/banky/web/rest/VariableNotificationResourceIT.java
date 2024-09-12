package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.VariableNotificationAsserts.*;
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
import sn.modeltech.banky.domain.VariableNotification;
import sn.modeltech.banky.repository.VariableNotificationRepository;
import sn.modeltech.banky.service.dto.VariableNotificationDTO;
import sn.modeltech.banky.service.mapper.VariableNotificationMapper;

/**
 * Integration tests for the {@link VariableNotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VariableNotificationResourceIT {

    private static final String DEFAULT_CODE_VARIABLE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_VARIABLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/variable-notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idVarNotification}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VariableNotificationRepository variableNotificationRepository;

    @Autowired
    private VariableNotificationMapper variableNotificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVariableNotificationMockMvc;

    private VariableNotification variableNotification;

    private VariableNotification insertedVariableNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariableNotification createEntity() {
        return new VariableNotification()
            .idVarNotification(UUID.randomUUID().toString())
            .codeVariable(DEFAULT_CODE_VARIABLE)
            .description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariableNotification createUpdatedEntity() {
        return new VariableNotification()
            .idVarNotification(UUID.randomUUID().toString())
            .codeVariable(UPDATED_CODE_VARIABLE)
            .description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    public void initTest() {
        variableNotification = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedVariableNotification != null) {
            variableNotificationRepository.delete(insertedVariableNotification);
            insertedVariableNotification = null;
        }
    }

    @Test
    @Transactional
    void createVariableNotification() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the VariableNotification
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(variableNotification);
        var returnedVariableNotificationDTO = om.readValue(
            restVariableNotificationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(variableNotificationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VariableNotificationDTO.class
        );

        // Validate the VariableNotification in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVariableNotification = variableNotificationMapper.toEntity(returnedVariableNotificationDTO);
        assertVariableNotificationUpdatableFieldsEquals(
            returnedVariableNotification,
            getPersistedVariableNotification(returnedVariableNotification)
        );

        insertedVariableNotification = returnedVariableNotification;
    }

    @Test
    @Transactional
    void createVariableNotificationWithExistingId() throws Exception {
        // Create the VariableNotification with an existing ID
        insertedVariableNotification = variableNotificationRepository.saveAndFlush(variableNotification);
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(variableNotification);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVariableNotificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(variableNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVariableNotifications() throws Exception {
        // Initialize the database
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());
        insertedVariableNotification = variableNotificationRepository.saveAndFlush(variableNotification);

        // Get all the variableNotificationList
        restVariableNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idVarNotification,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idVarNotification").value(hasItem(variableNotification.getIdVarNotification())))
            .andExpect(jsonPath("$.[*].codeVariable").value(hasItem(DEFAULT_CODE_VARIABLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getVariableNotification() throws Exception {
        // Initialize the database
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());
        insertedVariableNotification = variableNotificationRepository.saveAndFlush(variableNotification);

        // Get the variableNotification
        restVariableNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, variableNotification.getIdVarNotification()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idVarNotification").value(variableNotification.getIdVarNotification()))
            .andExpect(jsonPath("$.codeVariable").value(DEFAULT_CODE_VARIABLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingVariableNotification() throws Exception {
        // Get the variableNotification
        restVariableNotificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVariableNotification() throws Exception {
        // Initialize the database
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());
        insertedVariableNotification = variableNotificationRepository.saveAndFlush(variableNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the variableNotification
        VariableNotification updatedVariableNotification = variableNotificationRepository
            .findById(variableNotification.getIdVarNotification())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedVariableNotification are not directly saved in db
        em.detach(updatedVariableNotification);
        updatedVariableNotification.codeVariable(UPDATED_CODE_VARIABLE).description(UPDATED_DESCRIPTION);
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(updatedVariableNotification);

        restVariableNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, variableNotificationDTO.getIdVarNotification())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(variableNotificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the VariableNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVariableNotificationToMatchAllProperties(updatedVariableNotification);
    }

    @Test
    @Transactional
    void putNonExistingVariableNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());

        // Create the VariableNotification
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(variableNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariableNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, variableNotificationDTO.getIdVarNotification())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(variableNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVariableNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());

        // Create the VariableNotification
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(variableNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVariableNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(variableNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVariableNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());

        // Create the VariableNotification
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(variableNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVariableNotificationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(variableNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VariableNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVariableNotificationWithPatch() throws Exception {
        // Initialize the database
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());
        insertedVariableNotification = variableNotificationRepository.saveAndFlush(variableNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the variableNotification using partial update
        VariableNotification partialUpdatedVariableNotification = new VariableNotification();
        partialUpdatedVariableNotification.setIdVarNotification(variableNotification.getIdVarNotification());

        partialUpdatedVariableNotification.description(UPDATED_DESCRIPTION);

        restVariableNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVariableNotification.getIdVarNotification())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVariableNotification))
            )
            .andExpect(status().isOk());

        // Validate the VariableNotification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVariableNotificationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVariableNotification, variableNotification),
            getPersistedVariableNotification(variableNotification)
        );
    }

    @Test
    @Transactional
    void fullUpdateVariableNotificationWithPatch() throws Exception {
        // Initialize the database
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());
        insertedVariableNotification = variableNotificationRepository.saveAndFlush(variableNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the variableNotification using partial update
        VariableNotification partialUpdatedVariableNotification = new VariableNotification();
        partialUpdatedVariableNotification.setIdVarNotification(variableNotification.getIdVarNotification());

        partialUpdatedVariableNotification.codeVariable(UPDATED_CODE_VARIABLE).description(UPDATED_DESCRIPTION);

        restVariableNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVariableNotification.getIdVarNotification())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVariableNotification))
            )
            .andExpect(status().isOk());

        // Validate the VariableNotification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVariableNotificationUpdatableFieldsEquals(
            partialUpdatedVariableNotification,
            getPersistedVariableNotification(partialUpdatedVariableNotification)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVariableNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());

        // Create the VariableNotification
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(variableNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariableNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, variableNotificationDTO.getIdVarNotification())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(variableNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVariableNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());

        // Create the VariableNotification
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(variableNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVariableNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(variableNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVariableNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());

        // Create the VariableNotification
        VariableNotificationDTO variableNotificationDTO = variableNotificationMapper.toDto(variableNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVariableNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(variableNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VariableNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVariableNotification() throws Exception {
        // Initialize the database
        variableNotification.setIdVarNotification(UUID.randomUUID().toString());
        insertedVariableNotification = variableNotificationRepository.saveAndFlush(variableNotification);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the variableNotification
        restVariableNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, variableNotification.getIdVarNotification()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return variableNotificationRepository.count();
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

    protected VariableNotification getPersistedVariableNotification(VariableNotification variableNotification) {
        return variableNotificationRepository.findById(variableNotification.getIdVarNotification()).orElseThrow();
    }

    protected void assertPersistedVariableNotificationToMatchAllProperties(VariableNotification expectedVariableNotification) {
        assertVariableNotificationAllPropertiesEquals(
            expectedVariableNotification,
            getPersistedVariableNotification(expectedVariableNotification)
        );
    }

    protected void assertPersistedVariableNotificationToMatchUpdatableProperties(VariableNotification expectedVariableNotification) {
        assertVariableNotificationAllUpdatablePropertiesEquals(
            expectedVariableNotification,
            getPersistedVariableNotification(expectedVariableNotification)
        );
    }
}
