package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.MessageErreurAsserts.*;
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
import sn.modeltech.banky.domain.MessageErreur;
import sn.modeltech.banky.repository.MessageErreurRepository;
import sn.modeltech.banky.service.dto.MessageErreurDTO;
import sn.modeltech.banky.service.mapper.MessageErreurMapper;

/**
 * Integration tests for the {@link MessageErreurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MessageErreurResourceIT {

    private static final String DEFAULT_CODE_ERREUR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ERREUR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/message-erreurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idMessageErreur}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MessageErreurRepository messageErreurRepository;

    @Autowired
    private MessageErreurMapper messageErreurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageErreurMockMvc;

    private MessageErreur messageErreur;

    private MessageErreur insertedMessageErreur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageErreur createEntity() {
        return new MessageErreur()
            .idMessageErreur(UUID.randomUUID().toString())
            .codeErreur(DEFAULT_CODE_ERREUR)
            .description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageErreur createUpdatedEntity() {
        return new MessageErreur()
            .idMessageErreur(UUID.randomUUID().toString())
            .codeErreur(UPDATED_CODE_ERREUR)
            .description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    public void initTest() {
        messageErreur = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMessageErreur != null) {
            messageErreurRepository.delete(insertedMessageErreur);
            insertedMessageErreur = null;
        }
    }

    @Test
    @Transactional
    void createMessageErreur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MessageErreur
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(messageErreur);
        var returnedMessageErreurDTO = om.readValue(
            restMessageErreurMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(messageErreurDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MessageErreurDTO.class
        );

        // Validate the MessageErreur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMessageErreur = messageErreurMapper.toEntity(returnedMessageErreurDTO);
        assertMessageErreurUpdatableFieldsEquals(returnedMessageErreur, getPersistedMessageErreur(returnedMessageErreur));

        insertedMessageErreur = returnedMessageErreur;
    }

    @Test
    @Transactional
    void createMessageErreurWithExistingId() throws Exception {
        // Create the MessageErreur with an existing ID
        insertedMessageErreur = messageErreurRepository.saveAndFlush(messageErreur);
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(messageErreur);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageErreurMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(messageErreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageErreur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMessageErreurs() throws Exception {
        // Initialize the database
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());
        insertedMessageErreur = messageErreurRepository.saveAndFlush(messageErreur);

        // Get all the messageErreurList
        restMessageErreurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idMessageErreur,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idMessageErreur").value(hasItem(messageErreur.getIdMessageErreur())))
            .andExpect(jsonPath("$.[*].codeErreur").value(hasItem(DEFAULT_CODE_ERREUR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getMessageErreur() throws Exception {
        // Initialize the database
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());
        insertedMessageErreur = messageErreurRepository.saveAndFlush(messageErreur);

        // Get the messageErreur
        restMessageErreurMockMvc
            .perform(get(ENTITY_API_URL_ID, messageErreur.getIdMessageErreur()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idMessageErreur").value(messageErreur.getIdMessageErreur()))
            .andExpect(jsonPath("$.codeErreur").value(DEFAULT_CODE_ERREUR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingMessageErreur() throws Exception {
        // Get the messageErreur
        restMessageErreurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMessageErreur() throws Exception {
        // Initialize the database
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());
        insertedMessageErreur = messageErreurRepository.saveAndFlush(messageErreur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the messageErreur
        MessageErreur updatedMessageErreur = messageErreurRepository.findById(messageErreur.getIdMessageErreur()).orElseThrow();
        // Disconnect from session so that the updates on updatedMessageErreur are not directly saved in db
        em.detach(updatedMessageErreur);
        updatedMessageErreur.codeErreur(UPDATED_CODE_ERREUR).description(UPDATED_DESCRIPTION);
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(updatedMessageErreur);

        restMessageErreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageErreurDTO.getIdMessageErreur())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(messageErreurDTO))
            )
            .andExpect(status().isOk());

        // Validate the MessageErreur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMessageErreurToMatchAllProperties(updatedMessageErreur);
    }

    @Test
    @Transactional
    void putNonExistingMessageErreur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());

        // Create the MessageErreur
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(messageErreur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageErreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageErreurDTO.getIdMessageErreur())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(messageErreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageErreur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMessageErreur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());

        // Create the MessageErreur
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(messageErreur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageErreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(messageErreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageErreur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMessageErreur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());

        // Create the MessageErreur
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(messageErreur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageErreurMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(messageErreurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageErreur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMessageErreurWithPatch() throws Exception {
        // Initialize the database
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());
        insertedMessageErreur = messageErreurRepository.saveAndFlush(messageErreur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the messageErreur using partial update
        MessageErreur partialUpdatedMessageErreur = new MessageErreur();
        partialUpdatedMessageErreur.setIdMessageErreur(messageErreur.getIdMessageErreur());

        partialUpdatedMessageErreur.description(UPDATED_DESCRIPTION);

        restMessageErreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageErreur.getIdMessageErreur())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMessageErreur))
            )
            .andExpect(status().isOk());

        // Validate the MessageErreur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMessageErreurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMessageErreur, messageErreur),
            getPersistedMessageErreur(messageErreur)
        );
    }

    @Test
    @Transactional
    void fullUpdateMessageErreurWithPatch() throws Exception {
        // Initialize the database
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());
        insertedMessageErreur = messageErreurRepository.saveAndFlush(messageErreur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the messageErreur using partial update
        MessageErreur partialUpdatedMessageErreur = new MessageErreur();
        partialUpdatedMessageErreur.setIdMessageErreur(messageErreur.getIdMessageErreur());

        partialUpdatedMessageErreur.codeErreur(UPDATED_CODE_ERREUR).description(UPDATED_DESCRIPTION);

        restMessageErreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageErreur.getIdMessageErreur())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMessageErreur))
            )
            .andExpect(status().isOk());

        // Validate the MessageErreur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMessageErreurUpdatableFieldsEquals(partialUpdatedMessageErreur, getPersistedMessageErreur(partialUpdatedMessageErreur));
    }

    @Test
    @Transactional
    void patchNonExistingMessageErreur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());

        // Create the MessageErreur
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(messageErreur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageErreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, messageErreurDTO.getIdMessageErreur())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(messageErreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageErreur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMessageErreur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());

        // Create the MessageErreur
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(messageErreur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageErreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(messageErreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageErreur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMessageErreur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());

        // Create the MessageErreur
        MessageErreurDTO messageErreurDTO = messageErreurMapper.toDto(messageErreur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageErreurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(messageErreurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageErreur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMessageErreur() throws Exception {
        // Initialize the database
        messageErreur.setIdMessageErreur(UUID.randomUUID().toString());
        insertedMessageErreur = messageErreurRepository.saveAndFlush(messageErreur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the messageErreur
        restMessageErreurMockMvc
            .perform(delete(ENTITY_API_URL_ID, messageErreur.getIdMessageErreur()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return messageErreurRepository.count();
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

    protected MessageErreur getPersistedMessageErreur(MessageErreur messageErreur) {
        return messageErreurRepository.findById(messageErreur.getIdMessageErreur()).orElseThrow();
    }

    protected void assertPersistedMessageErreurToMatchAllProperties(MessageErreur expectedMessageErreur) {
        assertMessageErreurAllPropertiesEquals(expectedMessageErreur, getPersistedMessageErreur(expectedMessageErreur));
    }

    protected void assertPersistedMessageErreurToMatchUpdatableProperties(MessageErreur expectedMessageErreur) {
        assertMessageErreurAllUpdatablePropertiesEquals(expectedMessageErreur, getPersistedMessageErreur(expectedMessageErreur));
    }
}
