package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.BanqueAsserts.*;
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
import sn.modeltech.banky.domain.Banque;
import sn.modeltech.banky.domain.enumeration.Devise;
import sn.modeltech.banky.domain.enumeration.Langue;
import sn.modeltech.banky.repository.BanqueRepository;
import sn.modeltech.banky.service.dto.BanqueDTO;
import sn.modeltech.banky.service.mapper.BanqueMapper;

/**
 * Integration tests for the {@link BanqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BanqueResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Devise DEFAULT_DEVISE = Devise.XOF;
    private static final Devise UPDATED_DEVISE = Devise.XAF;

    private static final Langue DEFAULT_LANGUE = Langue.FR;
    private static final Langue UPDATED_LANGUE = Langue.EN;

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_SWIFT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SWIFT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_CODE_IBAN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_FUSEAU_HORAIRE = "AAAAAAAAAA";
    private static final String UPDATED_FUSEAU_HORAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_CUT_OFF_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CUT_OFF_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_PARTICIPANT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARTICIPANT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_PARTICIPANT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_PARTICIPANT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/banques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idBanque}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private BanqueMapper banqueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBanqueMockMvc;

    private Banque banque;

    private Banque insertedBanque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banque createEntity() {
        return new Banque()
            .idBanque(UUID.randomUUID().toString())
            .code(DEFAULT_CODE)
            .devise(DEFAULT_DEVISE)
            .langue(DEFAULT_LANGUE)
            .logo(DEFAULT_LOGO)
            .codeSwift(DEFAULT_CODE_SWIFT)
            .codeIban(DEFAULT_CODE_IBAN)
            .codePays(DEFAULT_CODE_PAYS)
            .fuseauHoraire(DEFAULT_FUSEAU_HORAIRE)
            .cutOffTime(DEFAULT_CUT_OFF_TIME)
            .codeParticipant(DEFAULT_CODE_PARTICIPANT)
            .libelleParticipant(DEFAULT_LIBELLE_PARTICIPANT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banque createUpdatedEntity() {
        return new Banque()
            .idBanque(UUID.randomUUID().toString())
            .code(UPDATED_CODE)
            .devise(UPDATED_DEVISE)
            .langue(UPDATED_LANGUE)
            .logo(UPDATED_LOGO)
            .codeSwift(UPDATED_CODE_SWIFT)
            .codeIban(UPDATED_CODE_IBAN)
            .codePays(UPDATED_CODE_PAYS)
            .fuseauHoraire(UPDATED_FUSEAU_HORAIRE)
            .cutOffTime(UPDATED_CUT_OFF_TIME)
            .codeParticipant(UPDATED_CODE_PARTICIPANT)
            .libelleParticipant(UPDATED_LIBELLE_PARTICIPANT);
    }

    @BeforeEach
    public void initTest() {
        banque = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBanque != null) {
            banqueRepository.delete(insertedBanque);
            insertedBanque = null;
        }
    }

    @Test
    @Transactional
    void createBanque() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);
        var returnedBanqueDTO = om.readValue(
            restBanqueMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(banqueDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BanqueDTO.class
        );

        // Validate the Banque in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBanque = banqueMapper.toEntity(returnedBanqueDTO);
        assertBanqueUpdatableFieldsEquals(returnedBanque, getPersistedBanque(returnedBanque));

        insertedBanque = returnedBanque;
    }

    @Test
    @Transactional
    void createBanqueWithExistingId() throws Exception {
        // Create the Banque with an existing ID
        insertedBanque = banqueRepository.saveAndFlush(banque);
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanqueMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(banqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanques() throws Exception {
        // Initialize the database
        banque.setIdBanque(UUID.randomUUID().toString());
        insertedBanque = banqueRepository.saveAndFlush(banque);

        // Get all the banqueList
        restBanqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idBanque,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idBanque").value(hasItem(banque.getIdBanque())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].langue").value(hasItem(DEFAULT_LANGUE.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].codeSwift").value(hasItem(DEFAULT_CODE_SWIFT)))
            .andExpect(jsonPath("$.[*].codeIban").value(hasItem(DEFAULT_CODE_IBAN)))
            .andExpect(jsonPath("$.[*].codePays").value(hasItem(DEFAULT_CODE_PAYS)))
            .andExpect(jsonPath("$.[*].fuseauHoraire").value(hasItem(DEFAULT_FUSEAU_HORAIRE)))
            .andExpect(jsonPath("$.[*].cutOffTime").value(hasItem(DEFAULT_CUT_OFF_TIME)))
            .andExpect(jsonPath("$.[*].codeParticipant").value(hasItem(DEFAULT_CODE_PARTICIPANT)))
            .andExpect(jsonPath("$.[*].libelleParticipant").value(hasItem(DEFAULT_LIBELLE_PARTICIPANT)));
    }

    @Test
    @Transactional
    void getBanque() throws Exception {
        // Initialize the database
        banque.setIdBanque(UUID.randomUUID().toString());
        insertedBanque = banqueRepository.saveAndFlush(banque);

        // Get the banque
        restBanqueMockMvc
            .perform(get(ENTITY_API_URL_ID, banque.getIdBanque()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idBanque").value(banque.getIdBanque()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE.toString()))
            .andExpect(jsonPath("$.langue").value(DEFAULT_LANGUE.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.codeSwift").value(DEFAULT_CODE_SWIFT))
            .andExpect(jsonPath("$.codeIban").value(DEFAULT_CODE_IBAN))
            .andExpect(jsonPath("$.codePays").value(DEFAULT_CODE_PAYS))
            .andExpect(jsonPath("$.fuseauHoraire").value(DEFAULT_FUSEAU_HORAIRE))
            .andExpect(jsonPath("$.cutOffTime").value(DEFAULT_CUT_OFF_TIME))
            .andExpect(jsonPath("$.codeParticipant").value(DEFAULT_CODE_PARTICIPANT))
            .andExpect(jsonPath("$.libelleParticipant").value(DEFAULT_LIBELLE_PARTICIPANT));
    }

    @Test
    @Transactional
    void getNonExistingBanque() throws Exception {
        // Get the banque
        restBanqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBanque() throws Exception {
        // Initialize the database
        banque.setIdBanque(UUID.randomUUID().toString());
        insertedBanque = banqueRepository.saveAndFlush(banque);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banque
        Banque updatedBanque = banqueRepository.findById(banque.getIdBanque()).orElseThrow();
        // Disconnect from session so that the updates on updatedBanque are not directly saved in db
        em.detach(updatedBanque);
        updatedBanque
            .code(UPDATED_CODE)
            .devise(UPDATED_DEVISE)
            .langue(UPDATED_LANGUE)
            .logo(UPDATED_LOGO)
            .codeSwift(UPDATED_CODE_SWIFT)
            .codeIban(UPDATED_CODE_IBAN)
            .codePays(UPDATED_CODE_PAYS)
            .fuseauHoraire(UPDATED_FUSEAU_HORAIRE)
            .cutOffTime(UPDATED_CUT_OFF_TIME)
            .codeParticipant(UPDATED_CODE_PARTICIPANT)
            .libelleParticipant(UPDATED_LIBELLE_PARTICIPANT);
        BanqueDTO banqueDTO = banqueMapper.toDto(updatedBanque);

        restBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banqueDTO.getIdBanque())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(banqueDTO))
            )
            .andExpect(status().isOk());

        // Validate the Banque in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBanqueToMatchAllProperties(updatedBanque);
    }

    @Test
    @Transactional
    void putNonExistingBanque() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banque.setIdBanque(UUID.randomUUID().toString());

        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banqueDTO.getIdBanque())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(banqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanque() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banque.setIdBanque(UUID.randomUUID().toString());

        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(banqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanque() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banque.setIdBanque(UUID.randomUUID().toString());

        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanqueMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(banqueDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banque in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBanqueWithPatch() throws Exception {
        // Initialize the database
        banque.setIdBanque(UUID.randomUUID().toString());
        insertedBanque = banqueRepository.saveAndFlush(banque);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banque using partial update
        Banque partialUpdatedBanque = new Banque();
        partialUpdatedBanque.setIdBanque(banque.getIdBanque());

        partialUpdatedBanque
            .devise(UPDATED_DEVISE)
            .langue(UPDATED_LANGUE)
            .codePays(UPDATED_CODE_PAYS)
            .fuseauHoraire(UPDATED_FUSEAU_HORAIRE)
            .cutOffTime(UPDATED_CUT_OFF_TIME)
            .libelleParticipant(UPDATED_LIBELLE_PARTICIPANT);

        restBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanque.getIdBanque())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBanque))
            )
            .andExpect(status().isOk());

        // Validate the Banque in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBanqueUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBanque, banque), getPersistedBanque(banque));
    }

    @Test
    @Transactional
    void fullUpdateBanqueWithPatch() throws Exception {
        // Initialize the database
        banque.setIdBanque(UUID.randomUUID().toString());
        insertedBanque = banqueRepository.saveAndFlush(banque);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banque using partial update
        Banque partialUpdatedBanque = new Banque();
        partialUpdatedBanque.setIdBanque(banque.getIdBanque());

        partialUpdatedBanque
            .code(UPDATED_CODE)
            .devise(UPDATED_DEVISE)
            .langue(UPDATED_LANGUE)
            .logo(UPDATED_LOGO)
            .codeSwift(UPDATED_CODE_SWIFT)
            .codeIban(UPDATED_CODE_IBAN)
            .codePays(UPDATED_CODE_PAYS)
            .fuseauHoraire(UPDATED_FUSEAU_HORAIRE)
            .cutOffTime(UPDATED_CUT_OFF_TIME)
            .codeParticipant(UPDATED_CODE_PARTICIPANT)
            .libelleParticipant(UPDATED_LIBELLE_PARTICIPANT);

        restBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanque.getIdBanque())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBanque))
            )
            .andExpect(status().isOk());

        // Validate the Banque in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBanqueUpdatableFieldsEquals(partialUpdatedBanque, getPersistedBanque(partialUpdatedBanque));
    }

    @Test
    @Transactional
    void patchNonExistingBanque() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banque.setIdBanque(UUID.randomUUID().toString());

        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banqueDTO.getIdBanque())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(banqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanque() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banque.setIdBanque(UUID.randomUUID().toString());

        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(banqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanque() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banque.setIdBanque(UUID.randomUUID().toString());

        // Create the Banque
        BanqueDTO banqueDTO = banqueMapper.toDto(banque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(banqueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banque in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanque() throws Exception {
        // Initialize the database
        banque.setIdBanque(UUID.randomUUID().toString());
        insertedBanque = banqueRepository.saveAndFlush(banque);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the banque
        restBanqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, banque.getIdBanque()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return banqueRepository.count();
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

    protected Banque getPersistedBanque(Banque banque) {
        return banqueRepository.findById(banque.getIdBanque()).orElseThrow();
    }

    protected void assertPersistedBanqueToMatchAllProperties(Banque expectedBanque) {
        assertBanqueAllPropertiesEquals(expectedBanque, getPersistedBanque(expectedBanque));
    }

    protected void assertPersistedBanqueToMatchUpdatableProperties(Banque expectedBanque) {
        assertBanqueAllUpdatablePropertiesEquals(expectedBanque, getPersistedBanque(expectedBanque));
    }
}
