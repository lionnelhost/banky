package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.AbonneAsserts.*;
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
import sn.modeltech.banky.domain.Abonne;
import sn.modeltech.banky.domain.enumeration.StatutAbonne;
import sn.modeltech.banky.domain.enumeration.TypePieceIdentite;
import sn.modeltech.banky.repository.AbonneRepository;
import sn.modeltech.banky.service.dto.AbonneDTO;
import sn.modeltech.banky.service.mapper.AbonneMapper;

/**
 * Integration tests for the {@link AbonneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AbonneResourceIT {

    private static final String DEFAULT_INDICE_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_INDICE_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_ABONNE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ABONNE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_ABONNE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_ABONNE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final TypePieceIdentite DEFAULT_TYPE_PIECE_IDENTITE = TypePieceIdentite.CNI;
    private static final TypePieceIdentite UPDATED_TYPE_PIECE_IDENTITE = TypePieceIdentite.PASSEPORT;

    private static final String DEFAULT_NUM_PIECE_IDENTITE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_PIECE_IDENTITE = "BBBBBBBBBB";

    private static final StatutAbonne DEFAULT_STATUT = StatutAbonne.DESACTIVE;
    private static final StatutAbonne UPDATED_STATUT = StatutAbonne.EN_COURS_ACTIVATION;

    private static final String DEFAULT_IDENTIFIANT = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIANT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/abonnes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idAbonne}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AbonneRepository abonneRepository;

    @Autowired
    private AbonneMapper abonneMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAbonneMockMvc;

    private Abonne abonne;

    private Abonne insertedAbonne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Abonne createEntity() {
        return new Abonne()
            .idAbonne(UUID.randomUUID().toString())
            .indiceClient(DEFAULT_INDICE_CLIENT)
            .nomAbonne(DEFAULT_NOM_ABONNE)
            .prenomAbonne(DEFAULT_PRENOM_ABONNE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .typePieceIdentite(DEFAULT_TYPE_PIECE_IDENTITE)
            .numPieceIdentite(DEFAULT_NUM_PIECE_IDENTITE)
            .statut(DEFAULT_STATUT)
            .identifiant(DEFAULT_IDENTIFIANT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Abonne createUpdatedEntity() {
        return new Abonne()
            .idAbonne(UUID.randomUUID().toString())
            .indiceClient(UPDATED_INDICE_CLIENT)
            .nomAbonne(UPDATED_NOM_ABONNE)
            .prenomAbonne(UPDATED_PRENOM_ABONNE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .typePieceIdentite(UPDATED_TYPE_PIECE_IDENTITE)
            .numPieceIdentite(UPDATED_NUM_PIECE_IDENTITE)
            .statut(UPDATED_STATUT)
            .identifiant(UPDATED_IDENTIFIANT);
    }

    @BeforeEach
    public void initTest() {
        abonne = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAbonne != null) {
            abonneRepository.delete(insertedAbonne);
            insertedAbonne = null;
        }
    }

    @Test
    @Transactional
    void createAbonne() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);
        var returnedAbonneDTO = om.readValue(
            restAbonneMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonneDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AbonneDTO.class
        );

        // Validate the Abonne in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAbonne = abonneMapper.toEntity(returnedAbonneDTO);
        assertAbonneUpdatableFieldsEquals(returnedAbonne, getPersistedAbonne(returnedAbonne));

        insertedAbonne = returnedAbonne;
    }

    @Test
    @Transactional
    void createAbonneWithExistingId() throws Exception {
        // Create the Abonne with an existing ID
        insertedAbonne = abonneRepository.saveAndFlush(abonne);
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbonneMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Abonne in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAbonnes() throws Exception {
        // Initialize the database
        abonne.setIdAbonne(UUID.randomUUID().toString());
        insertedAbonne = abonneRepository.saveAndFlush(abonne);

        // Get all the abonneList
        restAbonneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idAbonne,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idAbonne").value(hasItem(abonne.getIdAbonne())))
            .andExpect(jsonPath("$.[*].indiceClient").value(hasItem(DEFAULT_INDICE_CLIENT)))
            .andExpect(jsonPath("$.[*].nomAbonne").value(hasItem(DEFAULT_NOM_ABONNE)))
            .andExpect(jsonPath("$.[*].prenomAbonne").value(hasItem(DEFAULT_PRENOM_ABONNE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].typePieceIdentite").value(hasItem(DEFAULT_TYPE_PIECE_IDENTITE.toString())))
            .andExpect(jsonPath("$.[*].numPieceIdentite").value(hasItem(DEFAULT_NUM_PIECE_IDENTITE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].identifiant").value(hasItem(DEFAULT_IDENTIFIANT)));
    }

    @Test
    @Transactional
    void getAbonne() throws Exception {
        // Initialize the database
        abonne.setIdAbonne(UUID.randomUUID().toString());
        insertedAbonne = abonneRepository.saveAndFlush(abonne);

        // Get the abonne
        restAbonneMockMvc
            .perform(get(ENTITY_API_URL_ID, abonne.getIdAbonne()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idAbonne").value(abonne.getIdAbonne()))
            .andExpect(jsonPath("$.indiceClient").value(DEFAULT_INDICE_CLIENT))
            .andExpect(jsonPath("$.nomAbonne").value(DEFAULT_NOM_ABONNE))
            .andExpect(jsonPath("$.prenomAbonne").value(DEFAULT_PRENOM_ABONNE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.typePieceIdentite").value(DEFAULT_TYPE_PIECE_IDENTITE.toString()))
            .andExpect(jsonPath("$.numPieceIdentite").value(DEFAULT_NUM_PIECE_IDENTITE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.identifiant").value(DEFAULT_IDENTIFIANT));
    }

    @Test
    @Transactional
    void getNonExistingAbonne() throws Exception {
        // Get the abonne
        restAbonneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAbonne() throws Exception {
        // Initialize the database
        abonne.setIdAbonne(UUID.randomUUID().toString());
        insertedAbonne = abonneRepository.saveAndFlush(abonne);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the abonne
        Abonne updatedAbonne = abonneRepository.findById(abonne.getIdAbonne()).orElseThrow();
        // Disconnect from session so that the updates on updatedAbonne are not directly saved in db
        em.detach(updatedAbonne);
        updatedAbonne
            .indiceClient(UPDATED_INDICE_CLIENT)
            .nomAbonne(UPDATED_NOM_ABONNE)
            .prenomAbonne(UPDATED_PRENOM_ABONNE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .typePieceIdentite(UPDATED_TYPE_PIECE_IDENTITE)
            .numPieceIdentite(UPDATED_NUM_PIECE_IDENTITE)
            .statut(UPDATED_STATUT)
            .identifiant(UPDATED_IDENTIFIANT);
        AbonneDTO abonneDTO = abonneMapper.toDto(updatedAbonne);

        restAbonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, abonneDTO.getIdAbonne())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(abonneDTO))
            )
            .andExpect(status().isOk());

        // Validate the Abonne in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAbonneToMatchAllProperties(updatedAbonne);
    }

    @Test
    @Transactional
    void putNonExistingAbonne() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonne.setIdAbonne(UUID.randomUUID().toString());

        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, abonneDTO.getIdAbonne())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(abonneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Abonne in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAbonne() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonne.setIdAbonne(UUID.randomUUID().toString());

        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(abonneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Abonne in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAbonne() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonne.setIdAbonne(UUID.randomUUID().toString());

        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbonneMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Abonne in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAbonneWithPatch() throws Exception {
        // Initialize the database
        abonne.setIdAbonne(UUID.randomUUID().toString());
        insertedAbonne = abonneRepository.saveAndFlush(abonne);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the abonne using partial update
        Abonne partialUpdatedAbonne = new Abonne();
        partialUpdatedAbonne.setIdAbonne(abonne.getIdAbonne());

        partialUpdatedAbonne
            .indiceClient(UPDATED_INDICE_CLIENT)
            .typePieceIdentite(UPDATED_TYPE_PIECE_IDENTITE)
            .numPieceIdentite(UPDATED_NUM_PIECE_IDENTITE)
            .statut(UPDATED_STATUT)
            .identifiant(UPDATED_IDENTIFIANT);

        restAbonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbonne.getIdAbonne())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAbonne))
            )
            .andExpect(status().isOk());

        // Validate the Abonne in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAbonneUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAbonne, abonne), getPersistedAbonne(abonne));
    }

    @Test
    @Transactional
    void fullUpdateAbonneWithPatch() throws Exception {
        // Initialize the database
        abonne.setIdAbonne(UUID.randomUUID().toString());
        insertedAbonne = abonneRepository.saveAndFlush(abonne);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the abonne using partial update
        Abonne partialUpdatedAbonne = new Abonne();
        partialUpdatedAbonne.setIdAbonne(abonne.getIdAbonne());

        partialUpdatedAbonne
            .indiceClient(UPDATED_INDICE_CLIENT)
            .nomAbonne(UPDATED_NOM_ABONNE)
            .prenomAbonne(UPDATED_PRENOM_ABONNE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .typePieceIdentite(UPDATED_TYPE_PIECE_IDENTITE)
            .numPieceIdentite(UPDATED_NUM_PIECE_IDENTITE)
            .statut(UPDATED_STATUT)
            .identifiant(UPDATED_IDENTIFIANT);

        restAbonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbonne.getIdAbonne())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAbonne))
            )
            .andExpect(status().isOk());

        // Validate the Abonne in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAbonneUpdatableFieldsEquals(partialUpdatedAbonne, getPersistedAbonne(partialUpdatedAbonne));
    }

    @Test
    @Transactional
    void patchNonExistingAbonne() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonne.setIdAbonne(UUID.randomUUID().toString());

        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, abonneDTO.getIdAbonne())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(abonneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Abonne in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAbonne() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonne.setIdAbonne(UUID.randomUUID().toString());

        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(abonneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Abonne in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAbonne() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonne.setIdAbonne(UUID.randomUUID().toString());

        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbonneMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(abonneDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Abonne in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAbonne() throws Exception {
        // Initialize the database
        abonne.setIdAbonne(UUID.randomUUID().toString());
        insertedAbonne = abonneRepository.saveAndFlush(abonne);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the abonne
        restAbonneMockMvc
            .perform(delete(ENTITY_API_URL_ID, abonne.getIdAbonne()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return abonneRepository.count();
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

    protected Abonne getPersistedAbonne(Abonne abonne) {
        return abonneRepository.findById(abonne.getIdAbonne()).orElseThrow();
    }

    protected void assertPersistedAbonneToMatchAllProperties(Abonne expectedAbonne) {
        assertAbonneAllPropertiesEquals(expectedAbonne, getPersistedAbonne(expectedAbonne));
    }

    protected void assertPersistedAbonneToMatchUpdatableProperties(Abonne expectedAbonne) {
        assertAbonneAllUpdatablePropertiesEquals(expectedAbonne, getPersistedAbonne(expectedAbonne));
    }
}
