package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.ParametrageNotificationAsserts.*;
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
import sn.modeltech.banky.domain.ParametrageNotification;
import sn.modeltech.banky.repository.ParametrageNotificationRepository;
import sn.modeltech.banky.service.dto.ParametrageNotificationDTO;
import sn.modeltech.banky.service.mapper.ParametrageNotificationMapper;

/**
 * Integration tests for the {@link ParametrageNotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParametrageNotificationResourceIT {

    private static final String DEFAULT_OBJET_NOTIF = "AAAAAAAAAA";
    private static final String UPDATED_OBJET_NOTIF = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_NOTIF = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NOTIF = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parametrage-notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idParamNotif}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParametrageNotificationRepository parametrageNotificationRepository;

    @Autowired
    private ParametrageNotificationMapper parametrageNotificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametrageNotificationMockMvc;

    private ParametrageNotification parametrageNotification;

    private ParametrageNotification insertedParametrageNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParametrageNotification createEntity() {
        return new ParametrageNotification()
            .idParamNotif(UUID.randomUUID().toString())
            .objetNotif(DEFAULT_OBJET_NOTIF)
            .typeNotif(DEFAULT_TYPE_NOTIF)
            .contenu(DEFAULT_CONTENU);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParametrageNotification createUpdatedEntity() {
        return new ParametrageNotification()
            .idParamNotif(UUID.randomUUID().toString())
            .objetNotif(UPDATED_OBJET_NOTIF)
            .typeNotif(UPDATED_TYPE_NOTIF)
            .contenu(UPDATED_CONTENU);
    }

    @BeforeEach
    public void initTest() {
        parametrageNotification = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedParametrageNotification != null) {
            parametrageNotificationRepository.delete(insertedParametrageNotification);
            insertedParametrageNotification = null;
        }
    }

    @Test
    @Transactional
    void createParametrageNotification() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ParametrageNotification
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(parametrageNotification);
        var returnedParametrageNotificationDTO = om.readValue(
            restParametrageNotificationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(parametrageNotificationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ParametrageNotificationDTO.class
        );

        // Validate the ParametrageNotification in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedParametrageNotification = parametrageNotificationMapper.toEntity(returnedParametrageNotificationDTO);
        assertParametrageNotificationUpdatableFieldsEquals(
            returnedParametrageNotification,
            getPersistedParametrageNotification(returnedParametrageNotification)
        );

        insertedParametrageNotification = returnedParametrageNotification;
    }

    @Test
    @Transactional
    void createParametrageNotificationWithExistingId() throws Exception {
        // Create the ParametrageNotification with an existing ID
        insertedParametrageNotification = parametrageNotificationRepository.saveAndFlush(parametrageNotification);
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(parametrageNotification);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametrageNotificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParametrageNotifications() throws Exception {
        // Initialize the database
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());
        insertedParametrageNotification = parametrageNotificationRepository.saveAndFlush(parametrageNotification);

        // Get all the parametrageNotificationList
        restParametrageNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idParamNotif,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idParamNotif").value(hasItem(parametrageNotification.getIdParamNotif())))
            .andExpect(jsonPath("$.[*].objetNotif").value(hasItem(DEFAULT_OBJET_NOTIF)))
            .andExpect(jsonPath("$.[*].typeNotif").value(hasItem(DEFAULT_TYPE_NOTIF)))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU)));
    }

    @Test
    @Transactional
    void getParametrageNotification() throws Exception {
        // Initialize the database
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());
        insertedParametrageNotification = parametrageNotificationRepository.saveAndFlush(parametrageNotification);

        // Get the parametrageNotification
        restParametrageNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, parametrageNotification.getIdParamNotif()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idParamNotif").value(parametrageNotification.getIdParamNotif()))
            .andExpect(jsonPath("$.objetNotif").value(DEFAULT_OBJET_NOTIF))
            .andExpect(jsonPath("$.typeNotif").value(DEFAULT_TYPE_NOTIF))
            .andExpect(jsonPath("$.contenu").value(DEFAULT_CONTENU));
    }

    @Test
    @Transactional
    void getNonExistingParametrageNotification() throws Exception {
        // Get the parametrageNotification
        restParametrageNotificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParametrageNotification() throws Exception {
        // Initialize the database
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());
        insertedParametrageNotification = parametrageNotificationRepository.saveAndFlush(parametrageNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parametrageNotification
        ParametrageNotification updatedParametrageNotification = parametrageNotificationRepository
            .findById(parametrageNotification.getIdParamNotif())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedParametrageNotification are not directly saved in db
        em.detach(updatedParametrageNotification);
        updatedParametrageNotification.objetNotif(UPDATED_OBJET_NOTIF).typeNotif(UPDATED_TYPE_NOTIF).contenu(UPDATED_CONTENU);
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(updatedParametrageNotification);

        restParametrageNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parametrageNotificationDTO.getIdParamNotif())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageNotificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParametrageNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParametrageNotificationToMatchAllProperties(updatedParametrageNotification);
    }

    @Test
    @Transactional
    void putNonExistingParametrageNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());

        // Create the ParametrageNotification
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(parametrageNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametrageNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parametrageNotificationDTO.getIdParamNotif())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParametrageNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());

        // Create the ParametrageNotification
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(parametrageNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametrageNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParametrageNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());

        // Create the ParametrageNotification
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(parametrageNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametrageNotificationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parametrageNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParametrageNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParametrageNotificationWithPatch() throws Exception {
        // Initialize the database
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());
        insertedParametrageNotification = parametrageNotificationRepository.saveAndFlush(parametrageNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parametrageNotification using partial update
        ParametrageNotification partialUpdatedParametrageNotification = new ParametrageNotification();
        partialUpdatedParametrageNotification.setIdParamNotif(parametrageNotification.getIdParamNotif());

        partialUpdatedParametrageNotification.contenu(UPDATED_CONTENU);

        restParametrageNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParametrageNotification.getIdParamNotif())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParametrageNotification))
            )
            .andExpect(status().isOk());

        // Validate the ParametrageNotification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParametrageNotificationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParametrageNotification, parametrageNotification),
            getPersistedParametrageNotification(parametrageNotification)
        );
    }

    @Test
    @Transactional
    void fullUpdateParametrageNotificationWithPatch() throws Exception {
        // Initialize the database
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());
        insertedParametrageNotification = parametrageNotificationRepository.saveAndFlush(parametrageNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parametrageNotification using partial update
        ParametrageNotification partialUpdatedParametrageNotification = new ParametrageNotification();
        partialUpdatedParametrageNotification.setIdParamNotif(parametrageNotification.getIdParamNotif());

        partialUpdatedParametrageNotification.objetNotif(UPDATED_OBJET_NOTIF).typeNotif(UPDATED_TYPE_NOTIF).contenu(UPDATED_CONTENU);

        restParametrageNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParametrageNotification.getIdParamNotif())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParametrageNotification))
            )
            .andExpect(status().isOk());

        // Validate the ParametrageNotification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParametrageNotificationUpdatableFieldsEquals(
            partialUpdatedParametrageNotification,
            getPersistedParametrageNotification(partialUpdatedParametrageNotification)
        );
    }

    @Test
    @Transactional
    void patchNonExistingParametrageNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());

        // Create the ParametrageNotification
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(parametrageNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametrageNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parametrageNotificationDTO.getIdParamNotif())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parametrageNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParametrageNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());

        // Create the ParametrageNotification
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(parametrageNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametrageNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parametrageNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParametrageNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParametrageNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());

        // Create the ParametrageNotification
        ParametrageNotificationDTO parametrageNotificationDTO = parametrageNotificationMapper.toDto(parametrageNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametrageNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parametrageNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParametrageNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParametrageNotification() throws Exception {
        // Initialize the database
        parametrageNotification.setIdParamNotif(UUID.randomUUID().toString());
        insertedParametrageNotification = parametrageNotificationRepository.saveAndFlush(parametrageNotification);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parametrageNotification
        restParametrageNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, parametrageNotification.getIdParamNotif()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parametrageNotificationRepository.count();
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

    protected ParametrageNotification getPersistedParametrageNotification(ParametrageNotification parametrageNotification) {
        return parametrageNotificationRepository.findById(parametrageNotification.getIdParamNotif()).orElseThrow();
    }

    protected void assertPersistedParametrageNotificationToMatchAllProperties(ParametrageNotification expectedParametrageNotification) {
        assertParametrageNotificationAllPropertiesEquals(
            expectedParametrageNotification,
            getPersistedParametrageNotification(expectedParametrageNotification)
        );
    }

    protected void assertPersistedParametrageNotificationToMatchUpdatableProperties(
        ParametrageNotification expectedParametrageNotification
    ) {
        assertParametrageNotificationAllUpdatablePropertiesEquals(
            expectedParametrageNotification,
            getPersistedParametrageNotification(expectedParametrageNotification)
        );
    }
}
