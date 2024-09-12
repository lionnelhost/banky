package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.CompteBancaireAsserts.*;
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
import sn.modeltech.banky.domain.CompteBancaire;
import sn.modeltech.banky.domain.enumeration.StatutCompteBancaire;
import sn.modeltech.banky.repository.CompteBancaireRepository;
import sn.modeltech.banky.service.dto.CompteBancaireDTO;
import sn.modeltech.banky.service.mapper.CompteBancaireMapper;

/**
 * Integration tests for the {@link CompteBancaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompteBancaireResourceIT {

    private static final String DEFAULT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_AGE = "BBBBBBBBBB";

    private static final String DEFAULT_NCP = "AAAAAAAAAA";
    private static final String UPDATED_NCP = "BBBBBBBBBB";

    private static final String DEFAULT_SDE = "AAAAAAAAAA";
    private static final String UPDATED_SDE = "BBBBBBBBBB";

    private static final String DEFAULT_SIN = "AAAAAAAAAA";
    private static final String UPDATED_SIN = "BBBBBBBBBB";

    private static final String DEFAULT_SOLDE_DISPONIBLE = "AAAAAAAAAA";
    private static final String UPDATED_SOLDE_DISPONIBLE = "BBBBBBBBBB";

    private static final String DEFAULT_RIB = "AAAAAAAAAA";
    private static final String UPDATED_RIB = "BBBBBBBBBB";

    private static final StatutCompteBancaire DEFAULT_STATUS = StatutCompteBancaire.ACTIF;
    private static final StatutCompteBancaire UPDATED_STATUS = StatutCompteBancaire.INACTIF;

    private static final String ENTITY_API_URL = "/api/compte-bancaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idCompteBancaire}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CompteBancaireRepository compteBancaireRepository;

    @Autowired
    private CompteBancaireMapper compteBancaireMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompteBancaireMockMvc;

    private CompteBancaire compteBancaire;

    private CompteBancaire insertedCompteBancaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompteBancaire createEntity() {
        return new CompteBancaire()
            .idCompteBancaire(UUID.randomUUID().toString())
            .age(DEFAULT_AGE)
            .ncp(DEFAULT_NCP)
            .sde(DEFAULT_SDE)
            .sin(DEFAULT_SIN)
            .soldeDisponible(DEFAULT_SOLDE_DISPONIBLE)
            .rib(DEFAULT_RIB)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompteBancaire createUpdatedEntity() {
        return new CompteBancaire()
            .idCompteBancaire(UUID.randomUUID().toString())
            .age(UPDATED_AGE)
            .ncp(UPDATED_NCP)
            .sde(UPDATED_SDE)
            .sin(UPDATED_SIN)
            .soldeDisponible(UPDATED_SOLDE_DISPONIBLE)
            .rib(UPDATED_RIB)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    public void initTest() {
        compteBancaire = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCompteBancaire != null) {
            compteBancaireRepository.delete(insertedCompteBancaire);
            insertedCompteBancaire = null;
        }
    }

    @Test
    @Transactional
    void createCompteBancaire() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);
        var returnedCompteBancaireDTO = om.readValue(
            restCompteBancaireMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(compteBancaireDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CompteBancaireDTO.class
        );

        // Validate the CompteBancaire in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCompteBancaire = compteBancaireMapper.toEntity(returnedCompteBancaireDTO);
        assertCompteBancaireUpdatableFieldsEquals(returnedCompteBancaire, getPersistedCompteBancaire(returnedCompteBancaire));

        insertedCompteBancaire = returnedCompteBancaire;
    }

    @Test
    @Transactional
    void createCompteBancaireWithExistingId() throws Exception {
        // Create the CompteBancaire with an existing ID
        insertedCompteBancaire = compteBancaireRepository.saveAndFlush(compteBancaire);
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteBancaireMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(compteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompteBancaire in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompteBancaires() throws Exception {
        // Initialize the database
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());
        insertedCompteBancaire = compteBancaireRepository.saveAndFlush(compteBancaire);

        // Get all the compteBancaireList
        restCompteBancaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idCompteBancaire,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idCompteBancaire").value(hasItem(compteBancaire.getIdCompteBancaire())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].ncp").value(hasItem(DEFAULT_NCP)))
            .andExpect(jsonPath("$.[*].sde").value(hasItem(DEFAULT_SDE)))
            .andExpect(jsonPath("$.[*].sin").value(hasItem(DEFAULT_SIN)))
            .andExpect(jsonPath("$.[*].soldeDisponible").value(hasItem(DEFAULT_SOLDE_DISPONIBLE)))
            .andExpect(jsonPath("$.[*].rib").value(hasItem(DEFAULT_RIB)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());
        insertedCompteBancaire = compteBancaireRepository.saveAndFlush(compteBancaire);

        // Get the compteBancaire
        restCompteBancaireMockMvc
            .perform(get(ENTITY_API_URL_ID, compteBancaire.getIdCompteBancaire()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idCompteBancaire").value(compteBancaire.getIdCompteBancaire()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.ncp").value(DEFAULT_NCP))
            .andExpect(jsonPath("$.sde").value(DEFAULT_SDE))
            .andExpect(jsonPath("$.sin").value(DEFAULT_SIN))
            .andExpect(jsonPath("$.soldeDisponible").value(DEFAULT_SOLDE_DISPONIBLE))
            .andExpect(jsonPath("$.rib").value(DEFAULT_RIB))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompteBancaire() throws Exception {
        // Get the compteBancaire
        restCompteBancaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());
        insertedCompteBancaire = compteBancaireRepository.saveAndFlush(compteBancaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the compteBancaire
        CompteBancaire updatedCompteBancaire = compteBancaireRepository.findById(compteBancaire.getIdCompteBancaire()).orElseThrow();
        // Disconnect from session so that the updates on updatedCompteBancaire are not directly saved in db
        em.detach(updatedCompteBancaire);
        updatedCompteBancaire
            .age(UPDATED_AGE)
            .ncp(UPDATED_NCP)
            .sde(UPDATED_SDE)
            .sin(UPDATED_SIN)
            .soldeDisponible(UPDATED_SOLDE_DISPONIBLE)
            .rib(UPDATED_RIB)
            .status(UPDATED_STATUS);
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(updatedCompteBancaire);

        restCompteBancaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compteBancaireDTO.getIdCompteBancaire())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(compteBancaireDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompteBancaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCompteBancaireToMatchAllProperties(updatedCompteBancaire);
    }

    @Test
    @Transactional
    void putNonExistingCompteBancaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());

        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteBancaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compteBancaireDTO.getIdCompteBancaire())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(compteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompteBancaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompteBancaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());

        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompteBancaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(compteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompteBancaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompteBancaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());

        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompteBancaireMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(compteBancaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompteBancaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompteBancaireWithPatch() throws Exception {
        // Initialize the database
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());
        insertedCompteBancaire = compteBancaireRepository.saveAndFlush(compteBancaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the compteBancaire using partial update
        CompteBancaire partialUpdatedCompteBancaire = new CompteBancaire();
        partialUpdatedCompteBancaire.setIdCompteBancaire(compteBancaire.getIdCompteBancaire());

        partialUpdatedCompteBancaire.ncp(UPDATED_NCP).soldeDisponible(UPDATED_SOLDE_DISPONIBLE).rib(UPDATED_RIB).status(UPDATED_STATUS);

        restCompteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompteBancaire.getIdCompteBancaire())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompteBancaire))
            )
            .andExpect(status().isOk());

        // Validate the CompteBancaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompteBancaireUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCompteBancaire, compteBancaire),
            getPersistedCompteBancaire(compteBancaire)
        );
    }

    @Test
    @Transactional
    void fullUpdateCompteBancaireWithPatch() throws Exception {
        // Initialize the database
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());
        insertedCompteBancaire = compteBancaireRepository.saveAndFlush(compteBancaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the compteBancaire using partial update
        CompteBancaire partialUpdatedCompteBancaire = new CompteBancaire();
        partialUpdatedCompteBancaire.setIdCompteBancaire(compteBancaire.getIdCompteBancaire());

        partialUpdatedCompteBancaire
            .age(UPDATED_AGE)
            .ncp(UPDATED_NCP)
            .sde(UPDATED_SDE)
            .sin(UPDATED_SIN)
            .soldeDisponible(UPDATED_SOLDE_DISPONIBLE)
            .rib(UPDATED_RIB)
            .status(UPDATED_STATUS);

        restCompteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompteBancaire.getIdCompteBancaire())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompteBancaire))
            )
            .andExpect(status().isOk());

        // Validate the CompteBancaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompteBancaireUpdatableFieldsEquals(partialUpdatedCompteBancaire, getPersistedCompteBancaire(partialUpdatedCompteBancaire));
    }

    @Test
    @Transactional
    void patchNonExistingCompteBancaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());

        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, compteBancaireDTO.getIdCompteBancaire())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(compteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompteBancaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompteBancaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());

        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(compteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompteBancaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompteBancaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());

        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(compteBancaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompteBancaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaire.setIdCompteBancaire(UUID.randomUUID().toString());
        insertedCompteBancaire = compteBancaireRepository.saveAndFlush(compteBancaire);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the compteBancaire
        restCompteBancaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, compteBancaire.getIdCompteBancaire()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return compteBancaireRepository.count();
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

    protected CompteBancaire getPersistedCompteBancaire(CompteBancaire compteBancaire) {
        return compteBancaireRepository.findById(compteBancaire.getIdCompteBancaire()).orElseThrow();
    }

    protected void assertPersistedCompteBancaireToMatchAllProperties(CompteBancaire expectedCompteBancaire) {
        assertCompteBancaireAllPropertiesEquals(expectedCompteBancaire, getPersistedCompteBancaire(expectedCompteBancaire));
    }

    protected void assertPersistedCompteBancaireToMatchUpdatableProperties(CompteBancaire expectedCompteBancaire) {
        assertCompteBancaireAllUpdatablePropertiesEquals(expectedCompteBancaire, getPersistedCompteBancaire(expectedCompteBancaire));
    }
}
