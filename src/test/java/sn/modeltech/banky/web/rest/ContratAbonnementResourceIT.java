package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.ContratAbonnementAsserts.*;
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
import sn.modeltech.banky.domain.ContratAbonnement;
import sn.modeltech.banky.repository.ContratAbonnementRepository;
import sn.modeltech.banky.service.dto.ContratAbonnementDTO;
import sn.modeltech.banky.service.mapper.ContratAbonnementMapper;

/**
 * Integration tests for the {@link ContratAbonnementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContratAbonnementResourceIT {

    private static final String DEFAULT_ID_CONTRAT = "AAAAAAAAAA";
    private static final String UPDATED_ID_CONTRAT = "BBBBBBBBBB";

    private static final String DEFAULT_ID_ABONNE = "AAAAAAAAAA";
    private static final String UPDATED_ID_ABONNE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contrat-abonnements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContratAbonnementRepository contratAbonnementRepository;

    @Autowired
    private ContratAbonnementMapper contratAbonnementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContratAbonnementMockMvc;

    private ContratAbonnement contratAbonnement;

    private ContratAbonnement insertedContratAbonnement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContratAbonnement createEntity() {
        return new ContratAbonnement().idContrat(DEFAULT_ID_CONTRAT).idAbonne(DEFAULT_ID_ABONNE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContratAbonnement createUpdatedEntity() {
        return new ContratAbonnement().idContrat(UPDATED_ID_CONTRAT).idAbonne(UPDATED_ID_ABONNE);
    }

    @BeforeEach
    public void initTest() {
        contratAbonnement = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedContratAbonnement != null) {
            contratAbonnementRepository.delete(insertedContratAbonnement);
            insertedContratAbonnement = null;
        }
    }

    @Test
    @Transactional
    void createContratAbonnement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContratAbonnement
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(contratAbonnement);
        var returnedContratAbonnementDTO = om.readValue(
            restContratAbonnementMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(contratAbonnementDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContratAbonnementDTO.class
        );

        // Validate the ContratAbonnement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContratAbonnement = contratAbonnementMapper.toEntity(returnedContratAbonnementDTO);
        assertContratAbonnementUpdatableFieldsEquals(returnedContratAbonnement, getPersistedContratAbonnement(returnedContratAbonnement));

        insertedContratAbonnement = returnedContratAbonnement;
    }

    @Test
    @Transactional
    void createContratAbonnementWithExistingId() throws Exception {
        // Create the ContratAbonnement with an existing ID
        contratAbonnement.setId(1L);
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(contratAbonnement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratAbonnementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContratAbonnements() throws Exception {
        // Initialize the database
        insertedContratAbonnement = contratAbonnementRepository.saveAndFlush(contratAbonnement);

        // Get all the contratAbonnementList
        restContratAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contratAbonnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].idContrat").value(hasItem(DEFAULT_ID_CONTRAT)))
            .andExpect(jsonPath("$.[*].idAbonne").value(hasItem(DEFAULT_ID_ABONNE)));
    }

    @Test
    @Transactional
    void getContratAbonnement() throws Exception {
        // Initialize the database
        insertedContratAbonnement = contratAbonnementRepository.saveAndFlush(contratAbonnement);

        // Get the contratAbonnement
        restContratAbonnementMockMvc
            .perform(get(ENTITY_API_URL_ID, contratAbonnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contratAbonnement.getId().intValue()))
            .andExpect(jsonPath("$.idContrat").value(DEFAULT_ID_CONTRAT))
            .andExpect(jsonPath("$.idAbonne").value(DEFAULT_ID_ABONNE));
    }

    @Test
    @Transactional
    void getNonExistingContratAbonnement() throws Exception {
        // Get the contratAbonnement
        restContratAbonnementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContratAbonnement() throws Exception {
        // Initialize the database
        insertedContratAbonnement = contratAbonnementRepository.saveAndFlush(contratAbonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratAbonnement
        ContratAbonnement updatedContratAbonnement = contratAbonnementRepository.findById(contratAbonnement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContratAbonnement are not directly saved in db
        em.detach(updatedContratAbonnement);
        updatedContratAbonnement.idContrat(UPDATED_ID_CONTRAT).idAbonne(UPDATED_ID_ABONNE);
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(updatedContratAbonnement);

        restContratAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contratAbonnementDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContratAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContratAbonnementToMatchAllProperties(updatedContratAbonnement);
    }

    @Test
    @Transactional
    void putNonExistingContratAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnement.setId(longCount.incrementAndGet());

        // Create the ContratAbonnement
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(contratAbonnement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contratAbonnementDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContratAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnement.setId(longCount.incrementAndGet());

        // Create the ContratAbonnement
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(contratAbonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContratAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnement.setId(longCount.incrementAndGet());

        // Create the ContratAbonnement
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(contratAbonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contratAbonnementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContratAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContratAbonnementWithPatch() throws Exception {
        // Initialize the database
        insertedContratAbonnement = contratAbonnementRepository.saveAndFlush(contratAbonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratAbonnement using partial update
        ContratAbonnement partialUpdatedContratAbonnement = new ContratAbonnement();
        partialUpdatedContratAbonnement.setId(contratAbonnement.getId());

        partialUpdatedContratAbonnement.idContrat(UPDATED_ID_CONTRAT).idAbonne(UPDATED_ID_ABONNE);

        restContratAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContratAbonnement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContratAbonnement))
            )
            .andExpect(status().isOk());

        // Validate the ContratAbonnement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContratAbonnementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContratAbonnement, contratAbonnement),
            getPersistedContratAbonnement(contratAbonnement)
        );
    }

    @Test
    @Transactional
    void fullUpdateContratAbonnementWithPatch() throws Exception {
        // Initialize the database
        insertedContratAbonnement = contratAbonnementRepository.saveAndFlush(contratAbonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratAbonnement using partial update
        ContratAbonnement partialUpdatedContratAbonnement = new ContratAbonnement();
        partialUpdatedContratAbonnement.setId(contratAbonnement.getId());

        partialUpdatedContratAbonnement.idContrat(UPDATED_ID_CONTRAT).idAbonne(UPDATED_ID_ABONNE);

        restContratAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContratAbonnement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContratAbonnement))
            )
            .andExpect(status().isOk());

        // Validate the ContratAbonnement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContratAbonnementUpdatableFieldsEquals(
            partialUpdatedContratAbonnement,
            getPersistedContratAbonnement(partialUpdatedContratAbonnement)
        );
    }

    @Test
    @Transactional
    void patchNonExistingContratAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnement.setId(longCount.incrementAndGet());

        // Create the ContratAbonnement
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(contratAbonnement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contratAbonnementDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contratAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContratAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnement.setId(longCount.incrementAndGet());

        // Create the ContratAbonnement
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(contratAbonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contratAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContratAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratAbonnement.setId(longCount.incrementAndGet());

        // Create the ContratAbonnement
        ContratAbonnementDTO contratAbonnementDTO = contratAbonnementMapper.toDto(contratAbonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contratAbonnementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContratAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContratAbonnement() throws Exception {
        // Initialize the database
        insertedContratAbonnement = contratAbonnementRepository.saveAndFlush(contratAbonnement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contratAbonnement
        restContratAbonnementMockMvc
            .perform(delete(ENTITY_API_URL_ID, contratAbonnement.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contratAbonnementRepository.count();
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

    protected ContratAbonnement getPersistedContratAbonnement(ContratAbonnement contratAbonnement) {
        return contratAbonnementRepository.findById(contratAbonnement.getId()).orElseThrow();
    }

    protected void assertPersistedContratAbonnementToMatchAllProperties(ContratAbonnement expectedContratAbonnement) {
        assertContratAbonnementAllPropertiesEquals(expectedContratAbonnement, getPersistedContratAbonnement(expectedContratAbonnement));
    }

    protected void assertPersistedContratAbonnementToMatchUpdatableProperties(ContratAbonnement expectedContratAbonnement) {
        assertContratAbonnementAllUpdatablePropertiesEquals(
            expectedContratAbonnement,
            getPersistedContratAbonnement(expectedContratAbonnement)
        );
    }
}
