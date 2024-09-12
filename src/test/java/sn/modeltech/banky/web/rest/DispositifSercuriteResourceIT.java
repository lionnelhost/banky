package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.DispositifSercuriteAsserts.*;
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
import sn.modeltech.banky.domain.DispositifSercurite;
import sn.modeltech.banky.repository.DispositifSercuriteRepository;
import sn.modeltech.banky.service.dto.DispositifSercuriteDTO;
import sn.modeltech.banky.service.mapper.DispositifSercuriteMapper;

/**
 * Integration tests for the {@link DispositifSercuriteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DispositifSercuriteResourceIT {

    private static final String DEFAULT_ID_CANAL = "AAAAAAAAAA";
    private static final String UPDATED_ID_CANAL = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TYPE_TRANSACTION = "AAAAAAAAAA";
    private static final String UPDATED_ID_TYPE_TRANSACTION = "BBBBBBBBBB";

    private static final String DEFAULT_ID_DISPOSITIF = "AAAAAAAAAA";
    private static final String UPDATED_ID_DISPOSITIF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dispositif-sercurites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DispositifSercuriteRepository dispositifSercuriteRepository;

    @Autowired
    private DispositifSercuriteMapper dispositifSercuriteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDispositifSercuriteMockMvc;

    private DispositifSercurite dispositifSercurite;

    private DispositifSercurite insertedDispositifSercurite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispositifSercurite createEntity() {
        return new DispositifSercurite()
            .idCanal(DEFAULT_ID_CANAL)
            .idTypeTransaction(DEFAULT_ID_TYPE_TRANSACTION)
            .idDispositif(DEFAULT_ID_DISPOSITIF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispositifSercurite createUpdatedEntity() {
        return new DispositifSercurite()
            .idCanal(UPDATED_ID_CANAL)
            .idTypeTransaction(UPDATED_ID_TYPE_TRANSACTION)
            .idDispositif(UPDATED_ID_DISPOSITIF);
    }

    @BeforeEach
    public void initTest() {
        dispositifSercurite = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDispositifSercurite != null) {
            dispositifSercuriteRepository.delete(insertedDispositifSercurite);
            insertedDispositifSercurite = null;
        }
    }

    @Test
    @Transactional
    void createDispositifSercurite() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DispositifSercurite
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(dispositifSercurite);
        var returnedDispositifSercuriteDTO = om.readValue(
            restDispositifSercuriteMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(dispositifSercuriteDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DispositifSercuriteDTO.class
        );

        // Validate the DispositifSercurite in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDispositifSercurite = dispositifSercuriteMapper.toEntity(returnedDispositifSercuriteDTO);
        assertDispositifSercuriteUpdatableFieldsEquals(
            returnedDispositifSercurite,
            getPersistedDispositifSercurite(returnedDispositifSercurite)
        );

        insertedDispositifSercurite = returnedDispositifSercurite;
    }

    @Test
    @Transactional
    void createDispositifSercuriteWithExistingId() throws Exception {
        // Create the DispositifSercurite with an existing ID
        dispositifSercurite.setId(1L);
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(dispositifSercurite);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositifSercuriteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSercuriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSercurite in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDispositifSercurites() throws Exception {
        // Initialize the database
        insertedDispositifSercurite = dispositifSercuriteRepository.saveAndFlush(dispositifSercurite);

        // Get all the dispositifSercuriteList
        restDispositifSercuriteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispositifSercurite.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCanal").value(hasItem(DEFAULT_ID_CANAL)))
            .andExpect(jsonPath("$.[*].idTypeTransaction").value(hasItem(DEFAULT_ID_TYPE_TRANSACTION)))
            .andExpect(jsonPath("$.[*].idDispositif").value(hasItem(DEFAULT_ID_DISPOSITIF)));
    }

    @Test
    @Transactional
    void getDispositifSercurite() throws Exception {
        // Initialize the database
        insertedDispositifSercurite = dispositifSercuriteRepository.saveAndFlush(dispositifSercurite);

        // Get the dispositifSercurite
        restDispositifSercuriteMockMvc
            .perform(get(ENTITY_API_URL_ID, dispositifSercurite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dispositifSercurite.getId().intValue()))
            .andExpect(jsonPath("$.idCanal").value(DEFAULT_ID_CANAL))
            .andExpect(jsonPath("$.idTypeTransaction").value(DEFAULT_ID_TYPE_TRANSACTION))
            .andExpect(jsonPath("$.idDispositif").value(DEFAULT_ID_DISPOSITIF));
    }

    @Test
    @Transactional
    void getNonExistingDispositifSercurite() throws Exception {
        // Get the dispositifSercurite
        restDispositifSercuriteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDispositifSercurite() throws Exception {
        // Initialize the database
        insertedDispositifSercurite = dispositifSercuriteRepository.saveAndFlush(dispositifSercurite);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositifSercurite
        DispositifSercurite updatedDispositifSercurite = dispositifSercuriteRepository.findById(dispositifSercurite.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDispositifSercurite are not directly saved in db
        em.detach(updatedDispositifSercurite);
        updatedDispositifSercurite
            .idCanal(UPDATED_ID_CANAL)
            .idTypeTransaction(UPDATED_ID_TYPE_TRANSACTION)
            .idDispositif(UPDATED_ID_DISPOSITIF);
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(updatedDispositifSercurite);

        restDispositifSercuriteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositifSercuriteDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSercuriteDTO))
            )
            .andExpect(status().isOk());

        // Validate the DispositifSercurite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDispositifSercuriteToMatchAllProperties(updatedDispositifSercurite);
    }

    @Test
    @Transactional
    void putNonExistingDispositifSercurite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSercurite.setId(longCount.incrementAndGet());

        // Create the DispositifSercurite
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(dispositifSercurite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositifSercuriteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositifSercuriteDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSercuriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSercurite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDispositifSercurite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSercurite.setId(longCount.incrementAndGet());

        // Create the DispositifSercurite
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(dispositifSercurite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositifSercuriteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSercuriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSercurite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDispositifSercurite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSercurite.setId(longCount.incrementAndGet());

        // Create the DispositifSercurite
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(dispositifSercurite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositifSercuriteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositifSercuriteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DispositifSercurite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDispositifSercuriteWithPatch() throws Exception {
        // Initialize the database
        insertedDispositifSercurite = dispositifSercuriteRepository.saveAndFlush(dispositifSercurite);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositifSercurite using partial update
        DispositifSercurite partialUpdatedDispositifSercurite = new DispositifSercurite();
        partialUpdatedDispositifSercurite.setId(dispositifSercurite.getId());

        restDispositifSercuriteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositifSercurite.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDispositifSercurite))
            )
            .andExpect(status().isOk());

        // Validate the DispositifSercurite in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDispositifSercuriteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDispositifSercurite, dispositifSercurite),
            getPersistedDispositifSercurite(dispositifSercurite)
        );
    }

    @Test
    @Transactional
    void fullUpdateDispositifSercuriteWithPatch() throws Exception {
        // Initialize the database
        insertedDispositifSercurite = dispositifSercuriteRepository.saveAndFlush(dispositifSercurite);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositifSercurite using partial update
        DispositifSercurite partialUpdatedDispositifSercurite = new DispositifSercurite();
        partialUpdatedDispositifSercurite.setId(dispositifSercurite.getId());

        partialUpdatedDispositifSercurite
            .idCanal(UPDATED_ID_CANAL)
            .idTypeTransaction(UPDATED_ID_TYPE_TRANSACTION)
            .idDispositif(UPDATED_ID_DISPOSITIF);

        restDispositifSercuriteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositifSercurite.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDispositifSercurite))
            )
            .andExpect(status().isOk());

        // Validate the DispositifSercurite in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDispositifSercuriteUpdatableFieldsEquals(
            partialUpdatedDispositifSercurite,
            getPersistedDispositifSercurite(partialUpdatedDispositifSercurite)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDispositifSercurite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSercurite.setId(longCount.incrementAndGet());

        // Create the DispositifSercurite
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(dispositifSercurite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositifSercuriteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dispositifSercuriteDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositifSercuriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSercurite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDispositifSercurite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSercurite.setId(longCount.incrementAndGet());

        // Create the DispositifSercurite
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(dispositifSercurite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositifSercuriteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositifSercuriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositifSercurite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDispositifSercurite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositifSercurite.setId(longCount.incrementAndGet());

        // Create the DispositifSercurite
        DispositifSercuriteDTO dispositifSercuriteDTO = dispositifSercuriteMapper.toDto(dispositifSercurite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositifSercuriteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositifSercuriteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DispositifSercurite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDispositifSercurite() throws Exception {
        // Initialize the database
        insertedDispositifSercurite = dispositifSercuriteRepository.saveAndFlush(dispositifSercurite);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dispositifSercurite
        restDispositifSercuriteMockMvc
            .perform(delete(ENTITY_API_URL_ID, dispositifSercurite.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dispositifSercuriteRepository.count();
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

    protected DispositifSercurite getPersistedDispositifSercurite(DispositifSercurite dispositifSercurite) {
        return dispositifSercuriteRepository.findById(dispositifSercurite.getId()).orElseThrow();
    }

    protected void assertPersistedDispositifSercuriteToMatchAllProperties(DispositifSercurite expectedDispositifSercurite) {
        assertDispositifSercuriteAllPropertiesEquals(
            expectedDispositifSercurite,
            getPersistedDispositifSercurite(expectedDispositifSercurite)
        );
    }

    protected void assertPersistedDispositifSercuriteToMatchUpdatableProperties(DispositifSercurite expectedDispositifSercurite) {
        assertDispositifSercuriteAllUpdatablePropertiesEquals(
            expectedDispositifSercurite,
            getPersistedDispositifSercurite(expectedDispositifSercurite)
        );
    }
}
