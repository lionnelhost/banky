package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.ContratAbonnementCompteAsserts.*;
import static sn.modeltech.banky.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
import sn.modeltech.banky.domain.ContratAbonnementCompte;
import sn.modeltech.banky.repository.ContratAbonnementCompteRepository;
import sn.modeltech.banky.service.dto.ContratAbonnementCompteDTO;
import sn.modeltech.banky.service.mapper.ContratAbonnementCompteMapper;

/**
 * Integration tests for the {@link ContratAbonnementCompteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContratAbonnementCompteResourceIT {

    private static final String DEFAULT_ID_CONTRAT = "AAAAAAAAAA";
    private static final String UPDATED_ID_CONTRAT = "BBBBBBBBBB";

    private static final String DEFAULT_ID_ABONNE = "AAAAAAAAAA";
    private static final String UPDATED_ID_ABONNE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_COMPTE_BANCAIRE = "AAAAAAAAAA";
    private static final String UPDATED_ID_COMPTE_BANCAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contrat-abonnement-comptes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContratAbonnementCompteRepository contratAbonnementCompteRepository;

    @Autowired
    private ContratAbonnementCompteMapper contratAbonnementCompteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContratAbonnementCompteMockMvc;

    private ContratAbonnementCompte contratAbonnementCompte;

    private ContratAbonnementCompte insertedContratAbonnementCompte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContratAbonnementCompte createEntity() {
        return new ContratAbonnementCompte()
            .idContrat(DEFAULT_ID_CONTRAT)
            .idAbonne(DEFAULT_ID_ABONNE)
            .idCompteBancaire(DEFAULT_ID_COMPTE_BANCAIRE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContratAbonnementCompte createUpdatedEntity() {
        return new ContratAbonnementCompte()
            .idContrat(UPDATED_ID_CONTRAT)
            .idAbonne(UPDATED_ID_ABONNE)
            .idCompteBancaire(UPDATED_ID_COMPTE_BANCAIRE);
    }

    @BeforeEach
    public void initTest() {
        contratAbonnementCompte = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedContratAbonnementCompte != null) {
            contratAbonnementCompteRepository.delete(insertedContratAbonnementCompte);
            insertedContratAbonnementCompte = null;
        }
    }

    @Test
    @Transactional
    void createContratAbonnementCompte() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContratAbonnementCompte
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(contratAbonnementCompte);
        var returnedContratAbonnementCompteDTO = om.readValue(
            restContratAbonnementCompteMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContratAbonnementCompteDTO.class
        );

        // Validate the ContratAbonnementCompte in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContratAbonnementCompte = contratAbonnementCompteMapper.toEntity(returnedContratAbonnementCompteDTO);
        assertContratAbonnementCompteUpdatableFieldsEquals(
            returnedContratAbonnementCompte,
            getPersistedContratAbonnementCompte(returnedContratAbonnementCompte)
        );

        insertedContratAbonnementCompte = returnedContratAbonnementCompte;
    }

    @Test
    @Transactional
    void createContratAbonnementCompteWithExistingId() throws Exception {
        // Create the ContratAbonnementCompte with an existing ID
        contratAbonnementCompte.setId(1L);
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(contratAbonnementCompte);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratAbonnementCompteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnementCompte in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContratAbonnementComptes() throws Exception {
        // Initialize the database
        insertedContratAbonnementCompte = contratAbonnementCompteRepository.saveAndFlush(contratAbonnementCompte);

        // Get all the contratAbonnementCompteList
        restContratAbonnementCompteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contratAbonnementCompte.getId().intValue())))
            .andExpect(jsonPath("$.[*].idContrat").value(hasItem(DEFAULT_ID_CONTRAT)))
            .andExpect(jsonPath("$.[*].idAbonne").value(hasItem(DEFAULT_ID_ABONNE)))
            .andExpect(jsonPath("$.[*].idCompteBancaire").value(hasItem(DEFAULT_ID_COMPTE_BANCAIRE)));
    }

    @Test
    @Transactional
    void getContratAbonnementCompte() throws Exception {
        // Initialize the database
        insertedContratAbonnementCompte = contratAbonnementCompteRepository.saveAndFlush(contratAbonnementCompte);

        // Get the contratAbonnementCompte
        restContratAbonnementCompteMockMvc
            .perform(get(ENTITY_API_URL_ID, contratAbonnementCompte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contratAbonnementCompte.getId().intValue()))
            .andExpect(jsonPath("$.idContrat").value(DEFAULT_ID_CONTRAT))
            .andExpect(jsonPath("$.idAbonne").value(DEFAULT_ID_ABONNE))
            .andExpect(jsonPath("$.idCompteBancaire").value(DEFAULT_ID_COMPTE_BANCAIRE));
    }

    @Test
    @Transactional
    void getNonExistingContratAbonnementCompte() throws Exception {
        // Get the contratAbonnementCompte
        restContratAbonnementCompteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContratAbonnementCompte() throws Exception {
        // Initialize the database
        insertedContratAbonnementCompte = contratAbonnementCompteRepository.saveAndFlush(contratAbonnementCompte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratAbonnementCompte
        ContratAbonnementCompte updatedContratAbonnementCompte = contratAbonnementCompteRepository
            .findById(contratAbonnementCompte.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedContratAbonnementCompte are not directly saved in db
        em.detach(updatedContratAbonnementCompte);
        updatedContratAbonnementCompte
            .idContrat(UPDATED_ID_CONTRAT)
            .idAbonne(UPDATED_ID_ABONNE)
            .idCompteBancaire(UPDATED_ID_COMPTE_BANCAIRE);
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(updatedContratAbonnementCompte);

        restContratAbonnementCompteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contratAbonnementCompteDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContratAbonnementCompte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContratAbonnementCompteToMatchAllProperties(updatedContratAbonnementCompte);
    }

    @Test
    @Transactional
    void putNonExistingContratAbonnementCompte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnementCompte.setId(longCount.incrementAndGet());

        // Create the ContratAbonnementCompte
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(contratAbonnementCompte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratAbonnementCompteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contratAbonnementCompteDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnementCompte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContratAbonnementCompte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnementCompte.setId(longCount.incrementAndGet());

        // Create the ContratAbonnementCompte
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(contratAbonnementCompte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratAbonnementCompteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnementCompte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContratAbonnementCompte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnementCompte.setId(longCount.incrementAndGet());

        // Create the ContratAbonnementCompte
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(contratAbonnementCompte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratAbonnementCompteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContratAbonnementCompte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContratAbonnementCompteWithPatch() throws Exception {
        // Initialize the database
        insertedContratAbonnementCompte = contratAbonnementCompteRepository.saveAndFlush(contratAbonnementCompte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratAbonnementCompte using partial update
        ContratAbonnementCompte partialUpdatedContratAbonnementCompte = new ContratAbonnementCompte();
        partialUpdatedContratAbonnementCompte.setId(contratAbonnementCompte.getId());

        partialUpdatedContratAbonnementCompte.idCompteBancaire(UPDATED_ID_COMPTE_BANCAIRE);

        restContratAbonnementCompteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContratAbonnementCompte.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContratAbonnementCompte))
            )
            .andExpect(status().isOk());

        // Validate the ContratAbonnementCompte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContratAbonnementCompteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContratAbonnementCompte, contratAbonnementCompte),
            getPersistedContratAbonnementCompte(contratAbonnementCompte)
        );
    }

    @Test
    @Transactional
    void fullUpdateContratAbonnementCompteWithPatch() throws Exception {
        // Initialize the database
        insertedContratAbonnementCompte = contratAbonnementCompteRepository.saveAndFlush(contratAbonnementCompte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratAbonnementCompte using partial update
        ContratAbonnementCompte partialUpdatedContratAbonnementCompte = new ContratAbonnementCompte();
        partialUpdatedContratAbonnementCompte.setId(contratAbonnementCompte.getId());

        partialUpdatedContratAbonnementCompte
            .idContrat(UPDATED_ID_CONTRAT)
            .idAbonne(UPDATED_ID_ABONNE)
            .idCompteBancaire(UPDATED_ID_COMPTE_BANCAIRE);

        restContratAbonnementCompteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContratAbonnementCompte.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContratAbonnementCompte))
            )
            .andExpect(status().isOk());

        // Validate the ContratAbonnementCompte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContratAbonnementCompteUpdatableFieldsEquals(
            partialUpdatedContratAbonnementCompte,
            getPersistedContratAbonnementCompte(partialUpdatedContratAbonnementCompte)
        );
    }

    @Test
    @Transactional
    void patchNonExistingContratAbonnementCompte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnementCompte.setId(longCount.incrementAndGet());

        // Create the ContratAbonnementCompte
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(contratAbonnementCompte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratAbonnementCompteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contratAbonnementCompteDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnementCompte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContratAbonnementCompte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnementCompte.setId(longCount.incrementAndGet());

        // Create the ContratAbonnementCompte
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(contratAbonnementCompte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratAbonnementCompteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnementCompte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContratAbonnementCompte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnementCompte.setId(longCount.incrementAndGet());

        // Create the ContratAbonnementCompte
        ContratAbonnementCompteDTO contratAbonnementCompteDTO = contratAbonnementCompteMapper.toDto(contratAbonnementCompte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratAbonnementCompteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contratAbonnementCompteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContratAbonnementCompte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContratAbonnementCompte() throws Exception {
        // Initialize the database
        insertedContratAbonnementCompte = contratAbonnementCompteRepository.saveAndFlush(contratAbonnementCompte);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contratAbonnementCompte
        restContratAbonnementCompteMockMvc
            .perform(delete(ENTITY_API_URL_ID, contratAbonnementCompte.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contratAbonnementCompteRepository.count();
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

    protected ContratAbonnementCompte getPersistedContratAbonnementCompte(ContratAbonnementCompte contratAbonnementCompte) {
        return contratAbonnementCompteRepository.findById(contratAbonnementCompte.getId()).orElseThrow();
    }

    protected void assertPersistedContratAbonnementCompteToMatchAllProperties(ContratAbonnementCompte expectedContratAbonnementCompte) {
        assertContratAbonnementCompteAllPropertiesEquals(
            expectedContratAbonnementCompte,
            getPersistedContratAbonnementCompte(expectedContratAbonnementCompte)
        );
    }

    protected void assertPersistedContratAbonnementCompteToMatchUpdatableProperties(
        ContratAbonnementCompte expectedContratAbonnementCompte
    ) {
        assertContratAbonnementCompteAllUpdatablePropertiesEquals(
            expectedContratAbonnementCompte,
            getPersistedContratAbonnementCompte(expectedContratAbonnementCompte)
        );
    }
}
