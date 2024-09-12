package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.ParticipantAsserts.*;
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
import sn.modeltech.banky.domain.Participant;
import sn.modeltech.banky.repository.ParticipantRepository;
import sn.modeltech.banky.service.dto.ParticipantDTO;
import sn.modeltech.banky.service.mapper.ParticipantMapper;

/**
 * Integration tests for the {@link ParticipantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParticipantResourceIT {

    private static final String DEFAULT_CODE_PARTICIPANT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARTICIPANT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final String ENTITY_API_URL = "/api/participants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idParticipant}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantMapper participantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParticipantMockMvc;

    private Participant participant;

    private Participant insertedParticipant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createEntity() {
        return new Participant()
            .idParticipant(UUID.randomUUID().toString())
            .codeParticipant(DEFAULT_CODE_PARTICIPANT)
            .codeBanque(DEFAULT_CODE_BANQUE)
            .nomBanque(DEFAULT_NOM_BANQUE)
            .libelle(DEFAULT_LIBELLE)
            .pays(DEFAULT_PAYS)
            .isActif(DEFAULT_IS_ACTIF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createUpdatedEntity() {
        return new Participant()
            .idParticipant(UUID.randomUUID().toString())
            .codeParticipant(UPDATED_CODE_PARTICIPANT)
            .codeBanque(UPDATED_CODE_BANQUE)
            .nomBanque(UPDATED_NOM_BANQUE)
            .libelle(UPDATED_LIBELLE)
            .pays(UPDATED_PAYS)
            .isActif(UPDATED_IS_ACTIF);
    }

    @BeforeEach
    public void initTest() {
        participant = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedParticipant != null) {
            participantRepository.delete(insertedParticipant);
            insertedParticipant = null;
        }
    }

    @Test
    @Transactional
    void createParticipant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);
        var returnedParticipantDTO = om.readValue(
            restParticipantMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(participantDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ParticipantDTO.class
        );

        // Validate the Participant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedParticipant = participantMapper.toEntity(returnedParticipantDTO);
        assertParticipantUpdatableFieldsEquals(returnedParticipant, getPersistedParticipant(returnedParticipant));

        insertedParticipant = returnedParticipant;
    }

    @Test
    @Transactional
    void createParticipantWithExistingId() throws Exception {
        // Create the Participant with an existing ID
        insertedParticipant = participantRepository.saveAndFlush(participant);
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipantMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(participantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParticipants() throws Exception {
        // Initialize the database
        participant.setIdParticipant(UUID.randomUUID().toString());
        insertedParticipant = participantRepository.saveAndFlush(participant);

        // Get all the participantList
        restParticipantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idParticipant,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idParticipant").value(hasItem(participant.getIdParticipant())))
            .andExpect(jsonPath("$.[*].codeParticipant").value(hasItem(DEFAULT_CODE_PARTICIPANT)))
            .andExpect(jsonPath("$.[*].codeBanque").value(hasItem(DEFAULT_CODE_BANQUE)))
            .andExpect(jsonPath("$.[*].nomBanque").value(hasItem(DEFAULT_NOM_BANQUE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())));
    }

    @Test
    @Transactional
    void getParticipant() throws Exception {
        // Initialize the database
        participant.setIdParticipant(UUID.randomUUID().toString());
        insertedParticipant = participantRepository.saveAndFlush(participant);

        // Get the participant
        restParticipantMockMvc
            .perform(get(ENTITY_API_URL_ID, participant.getIdParticipant()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idParticipant").value(participant.getIdParticipant()))
            .andExpect(jsonPath("$.codeParticipant").value(DEFAULT_CODE_PARTICIPANT))
            .andExpect(jsonPath("$.codeBanque").value(DEFAULT_CODE_BANQUE))
            .andExpect(jsonPath("$.nomBanque").value(DEFAULT_NOM_BANQUE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingParticipant() throws Exception {
        // Get the participant
        restParticipantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParticipant() throws Exception {
        // Initialize the database
        participant.setIdParticipant(UUID.randomUUID().toString());
        insertedParticipant = participantRepository.saveAndFlush(participant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the participant
        Participant updatedParticipant = participantRepository.findById(participant.getIdParticipant()).orElseThrow();
        // Disconnect from session so that the updates on updatedParticipant are not directly saved in db
        em.detach(updatedParticipant);
        updatedParticipant
            .codeParticipant(UPDATED_CODE_PARTICIPANT)
            .codeBanque(UPDATED_CODE_BANQUE)
            .nomBanque(UPDATED_NOM_BANQUE)
            .libelle(UPDATED_LIBELLE)
            .pays(UPDATED_PAYS)
            .isActif(UPDATED_IS_ACTIF);
        ParticipantDTO participantDTO = participantMapper.toDto(updatedParticipant);

        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participantDTO.getIdParticipant())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(participantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParticipantToMatchAllProperties(updatedParticipant);
    }

    @Test
    @Transactional
    void putNonExistingParticipant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participant.setIdParticipant(UUID.randomUUID().toString());

        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participantDTO.getIdParticipant())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(participantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParticipant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participant.setIdParticipant(UUID.randomUUID().toString());

        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(participantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParticipant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participant.setIdParticipant(UUID.randomUUID().toString());

        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(participantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParticipantWithPatch() throws Exception {
        // Initialize the database
        participant.setIdParticipant(UUID.randomUUID().toString());
        insertedParticipant = participantRepository.saveAndFlush(participant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the participant using partial update
        Participant partialUpdatedParticipant = new Participant();
        partialUpdatedParticipant.setIdParticipant(participant.getIdParticipant());

        partialUpdatedParticipant.codeBanque(UPDATED_CODE_BANQUE);

        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipant.getIdParticipant())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParticipant))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParticipantUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParticipant, participant),
            getPersistedParticipant(participant)
        );
    }

    @Test
    @Transactional
    void fullUpdateParticipantWithPatch() throws Exception {
        // Initialize the database
        participant.setIdParticipant(UUID.randomUUID().toString());
        insertedParticipant = participantRepository.saveAndFlush(participant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the participant using partial update
        Participant partialUpdatedParticipant = new Participant();
        partialUpdatedParticipant.setIdParticipant(participant.getIdParticipant());

        partialUpdatedParticipant
            .codeParticipant(UPDATED_CODE_PARTICIPANT)
            .codeBanque(UPDATED_CODE_BANQUE)
            .nomBanque(UPDATED_NOM_BANQUE)
            .libelle(UPDATED_LIBELLE)
            .pays(UPDATED_PAYS)
            .isActif(UPDATED_IS_ACTIF);

        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipant.getIdParticipant())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParticipant))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParticipantUpdatableFieldsEquals(partialUpdatedParticipant, getPersistedParticipant(partialUpdatedParticipant));
    }

    @Test
    @Transactional
    void patchNonExistingParticipant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participant.setIdParticipant(UUID.randomUUID().toString());

        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, participantDTO.getIdParticipant())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(participantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParticipant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participant.setIdParticipant(UUID.randomUUID().toString());

        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(participantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParticipant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participant.setIdParticipant(UUID.randomUUID().toString());

        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(participantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParticipant() throws Exception {
        // Initialize the database
        participant.setIdParticipant(UUID.randomUUID().toString());
        insertedParticipant = participantRepository.saveAndFlush(participant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the participant
        restParticipantMockMvc
            .perform(delete(ENTITY_API_URL_ID, participant.getIdParticipant()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return participantRepository.count();
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

    protected Participant getPersistedParticipant(Participant participant) {
        return participantRepository.findById(participant.getIdParticipant()).orElseThrow();
    }

    protected void assertPersistedParticipantToMatchAllProperties(Participant expectedParticipant) {
        assertParticipantAllPropertiesEquals(expectedParticipant, getPersistedParticipant(expectedParticipant));
    }

    protected void assertPersistedParticipantToMatchUpdatableProperties(Participant expectedParticipant) {
        assertParticipantAllUpdatablePropertiesEquals(expectedParticipant, getPersistedParticipant(expectedParticipant));
    }
}
