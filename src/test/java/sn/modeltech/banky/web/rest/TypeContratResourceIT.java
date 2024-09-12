package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.TypeContratAsserts.*;
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
import sn.modeltech.banky.domain.TypeContrat;
import sn.modeltech.banky.repository.TypeContratRepository;
import sn.modeltech.banky.service.dto.TypeContratDTO;
import sn.modeltech.banky.service.mapper.TypeContratMapper;

/**
 * Integration tests for the {@link TypeContratResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeContratResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-contrats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idTypeContrat}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeContratRepository typeContratRepository;

    @Autowired
    private TypeContratMapper typeContratMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeContratMockMvc;

    private TypeContrat typeContrat;

    private TypeContrat insertedTypeContrat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeContrat createEntity() {
        return new TypeContrat().idTypeContrat(UUID.randomUUID().toString()).libelle(DEFAULT_LIBELLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeContrat createUpdatedEntity() {
        return new TypeContrat().idTypeContrat(UUID.randomUUID().toString()).libelle(UPDATED_LIBELLE);
    }

    @BeforeEach
    public void initTest() {
        typeContrat = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeContrat != null) {
            typeContratRepository.delete(insertedTypeContrat);
            insertedTypeContrat = null;
        }
    }

    @Test
    @Transactional
    void createTypeContrat() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeContrat
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(typeContrat);
        var returnedTypeContratDTO = om.readValue(
            restTypeContratMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeContratDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeContratDTO.class
        );

        // Validate the TypeContrat in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeContrat = typeContratMapper.toEntity(returnedTypeContratDTO);
        assertTypeContratUpdatableFieldsEquals(returnedTypeContrat, getPersistedTypeContrat(returnedTypeContrat));

        insertedTypeContrat = returnedTypeContrat;
    }

    @Test
    @Transactional
    void createTypeContratWithExistingId() throws Exception {
        // Create the TypeContrat with an existing ID
        insertedTypeContrat = typeContratRepository.saveAndFlush(typeContrat);
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(typeContrat);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeContratMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeContratDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeContrats() throws Exception {
        // Initialize the database
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());
        insertedTypeContrat = typeContratRepository.saveAndFlush(typeContrat);

        // Get all the typeContratList
        restTypeContratMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idTypeContrat,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idTypeContrat").value(hasItem(typeContrat.getIdTypeContrat())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getTypeContrat() throws Exception {
        // Initialize the database
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());
        insertedTypeContrat = typeContratRepository.saveAndFlush(typeContrat);

        // Get the typeContrat
        restTypeContratMockMvc
            .perform(get(ENTITY_API_URL_ID, typeContrat.getIdTypeContrat()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idTypeContrat").value(typeContrat.getIdTypeContrat()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingTypeContrat() throws Exception {
        // Get the typeContrat
        restTypeContratMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeContrat() throws Exception {
        // Initialize the database
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());
        insertedTypeContrat = typeContratRepository.saveAndFlush(typeContrat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeContrat
        TypeContrat updatedTypeContrat = typeContratRepository.findById(typeContrat.getIdTypeContrat()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeContrat are not directly saved in db
        em.detach(updatedTypeContrat);
        updatedTypeContrat.libelle(UPDATED_LIBELLE);
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(updatedTypeContrat);

        restTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeContratDTO.getIdTypeContrat())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeContratDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeContrat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeContratToMatchAllProperties(updatedTypeContrat);
    }

    @Test
    @Transactional
    void putNonExistingTypeContrat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());

        // Create the TypeContrat
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(typeContrat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeContratDTO.getIdTypeContrat())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeContratDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeContrat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());

        // Create the TypeContrat
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(typeContrat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeContratDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeContrat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());

        // Create the TypeContrat
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(typeContrat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeContratDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeContrat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeContratWithPatch() throws Exception {
        // Initialize the database
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());
        insertedTypeContrat = typeContratRepository.saveAndFlush(typeContrat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeContrat using partial update
        TypeContrat partialUpdatedTypeContrat = new TypeContrat();
        partialUpdatedTypeContrat.setIdTypeContrat(typeContrat.getIdTypeContrat());

        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeContrat.getIdTypeContrat())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeContrat))
            )
            .andExpect(status().isOk());

        // Validate the TypeContrat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeContratUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeContrat, typeContrat),
            getPersistedTypeContrat(typeContrat)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeContratWithPatch() throws Exception {
        // Initialize the database
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());
        insertedTypeContrat = typeContratRepository.saveAndFlush(typeContrat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeContrat using partial update
        TypeContrat partialUpdatedTypeContrat = new TypeContrat();
        partialUpdatedTypeContrat.setIdTypeContrat(typeContrat.getIdTypeContrat());

        partialUpdatedTypeContrat.libelle(UPDATED_LIBELLE);

        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeContrat.getIdTypeContrat())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeContrat))
            )
            .andExpect(status().isOk());

        // Validate the TypeContrat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeContratUpdatableFieldsEquals(partialUpdatedTypeContrat, getPersistedTypeContrat(partialUpdatedTypeContrat));
    }

    @Test
    @Transactional
    void patchNonExistingTypeContrat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());

        // Create the TypeContrat
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(typeContrat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeContratDTO.getIdTypeContrat())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeContratDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeContrat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());

        // Create the TypeContrat
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(typeContrat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeContratDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeContrat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());

        // Create the TypeContrat
        TypeContratDTO typeContratDTO = typeContratMapper.toDto(typeContrat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(typeContratDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeContrat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeContrat() throws Exception {
        // Initialize the database
        typeContrat.setIdTypeContrat(UUID.randomUUID().toString());
        insertedTypeContrat = typeContratRepository.saveAndFlush(typeContrat);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeContrat
        restTypeContratMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeContrat.getIdTypeContrat()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeContratRepository.count();
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

    protected TypeContrat getPersistedTypeContrat(TypeContrat typeContrat) {
        return typeContratRepository.findById(typeContrat.getIdTypeContrat()).orElseThrow();
    }

    protected void assertPersistedTypeContratToMatchAllProperties(TypeContrat expectedTypeContrat) {
        assertTypeContratAllPropertiesEquals(expectedTypeContrat, getPersistedTypeContrat(expectedTypeContrat));
    }

    protected void assertPersistedTypeContratToMatchUpdatableProperties(TypeContrat expectedTypeContrat) {
        assertTypeContratAllUpdatablePropertiesEquals(expectedTypeContrat, getPersistedTypeContrat(expectedTypeContrat));
    }
}
