package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.TypeClientAsserts.*;
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
import sn.modeltech.banky.domain.TypeClient;
import sn.modeltech.banky.repository.TypeClientRepository;
import sn.modeltech.banky.service.dto.TypeClientDTO;
import sn.modeltech.banky.service.mapper.TypeClientMapper;

/**
 * Integration tests for the {@link TypeClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeClientResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idTypeClient}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeClientRepository typeClientRepository;

    @Autowired
    private TypeClientMapper typeClientMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeClientMockMvc;

    private TypeClient typeClient;

    private TypeClient insertedTypeClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeClient createEntity() {
        return new TypeClient().idTypeClient(UUID.randomUUID().toString()).libelle(DEFAULT_LIBELLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeClient createUpdatedEntity() {
        return new TypeClient().idTypeClient(UUID.randomUUID().toString()).libelle(UPDATED_LIBELLE);
    }

    @BeforeEach
    public void initTest() {
        typeClient = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeClient != null) {
            typeClientRepository.delete(insertedTypeClient);
            insertedTypeClient = null;
        }
    }

    @Test
    @Transactional
    void createTypeClient() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeClient
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(typeClient);
        var returnedTypeClientDTO = om.readValue(
            restTypeClientMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeClientDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeClientDTO.class
        );

        // Validate the TypeClient in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeClient = typeClientMapper.toEntity(returnedTypeClientDTO);
        assertTypeClientUpdatableFieldsEquals(returnedTypeClient, getPersistedTypeClient(returnedTypeClient));

        insertedTypeClient = returnedTypeClient;
    }

    @Test
    @Transactional
    void createTypeClientWithExistingId() throws Exception {
        // Create the TypeClient with an existing ID
        insertedTypeClient = typeClientRepository.saveAndFlush(typeClient);
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(typeClient);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeClientMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeClientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeClient in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeClients() throws Exception {
        // Initialize the database
        typeClient.setIdTypeClient(UUID.randomUUID().toString());
        insertedTypeClient = typeClientRepository.saveAndFlush(typeClient);

        // Get all the typeClientList
        restTypeClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idTypeClient,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idTypeClient").value(hasItem(typeClient.getIdTypeClient())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getTypeClient() throws Exception {
        // Initialize the database
        typeClient.setIdTypeClient(UUID.randomUUID().toString());
        insertedTypeClient = typeClientRepository.saveAndFlush(typeClient);

        // Get the typeClient
        restTypeClientMockMvc
            .perform(get(ENTITY_API_URL_ID, typeClient.getIdTypeClient()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idTypeClient").value(typeClient.getIdTypeClient()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingTypeClient() throws Exception {
        // Get the typeClient
        restTypeClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeClient() throws Exception {
        // Initialize the database
        typeClient.setIdTypeClient(UUID.randomUUID().toString());
        insertedTypeClient = typeClientRepository.saveAndFlush(typeClient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeClient
        TypeClient updatedTypeClient = typeClientRepository.findById(typeClient.getIdTypeClient()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeClient are not directly saved in db
        em.detach(updatedTypeClient);
        updatedTypeClient.libelle(UPDATED_LIBELLE);
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(updatedTypeClient);

        restTypeClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeClientDTO.getIdTypeClient())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeClientDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeClientToMatchAllProperties(updatedTypeClient);
    }

    @Test
    @Transactional
    void putNonExistingTypeClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeClient.setIdTypeClient(UUID.randomUUID().toString());

        // Create the TypeClient
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(typeClient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeClientDTO.getIdTypeClient())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeClientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeClient.setIdTypeClient(UUID.randomUUID().toString());

        // Create the TypeClient
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(typeClient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeClientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeClient.setIdTypeClient(UUID.randomUUID().toString());

        // Create the TypeClient
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(typeClient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeClientMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeClientDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeClientWithPatch() throws Exception {
        // Initialize the database
        typeClient.setIdTypeClient(UUID.randomUUID().toString());
        insertedTypeClient = typeClientRepository.saveAndFlush(typeClient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeClient using partial update
        TypeClient partialUpdatedTypeClient = new TypeClient();
        partialUpdatedTypeClient.setIdTypeClient(typeClient.getIdTypeClient());

        partialUpdatedTypeClient.libelle(UPDATED_LIBELLE);

        restTypeClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeClient.getIdTypeClient())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeClient))
            )
            .andExpect(status().isOk());

        // Validate the TypeClient in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeClientUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeClient, typeClient),
            getPersistedTypeClient(typeClient)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeClientWithPatch() throws Exception {
        // Initialize the database
        typeClient.setIdTypeClient(UUID.randomUUID().toString());
        insertedTypeClient = typeClientRepository.saveAndFlush(typeClient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeClient using partial update
        TypeClient partialUpdatedTypeClient = new TypeClient();
        partialUpdatedTypeClient.setIdTypeClient(typeClient.getIdTypeClient());

        partialUpdatedTypeClient.libelle(UPDATED_LIBELLE);

        restTypeClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeClient.getIdTypeClient())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeClient))
            )
            .andExpect(status().isOk());

        // Validate the TypeClient in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeClientUpdatableFieldsEquals(partialUpdatedTypeClient, getPersistedTypeClient(partialUpdatedTypeClient));
    }

    @Test
    @Transactional
    void patchNonExistingTypeClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeClient.setIdTypeClient(UUID.randomUUID().toString());

        // Create the TypeClient
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(typeClient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeClientDTO.getIdTypeClient())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeClientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeClient.setIdTypeClient(UUID.randomUUID().toString());

        // Create the TypeClient
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(typeClient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeClientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeClient.setIdTypeClient(UUID.randomUUID().toString());

        // Create the TypeClient
        TypeClientDTO typeClientDTO = typeClientMapper.toDto(typeClient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeClientMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(typeClientDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeClient() throws Exception {
        // Initialize the database
        typeClient.setIdTypeClient(UUID.randomUUID().toString());
        insertedTypeClient = typeClientRepository.saveAndFlush(typeClient);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeClient
        restTypeClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeClient.getIdTypeClient()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeClientRepository.count();
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

    protected TypeClient getPersistedTypeClient(TypeClient typeClient) {
        return typeClientRepository.findById(typeClient.getIdTypeClient()).orElseThrow();
    }

    protected void assertPersistedTypeClientToMatchAllProperties(TypeClient expectedTypeClient) {
        assertTypeClientAllPropertiesEquals(expectedTypeClient, getPersistedTypeClient(expectedTypeClient));
    }

    protected void assertPersistedTypeClientToMatchUpdatableProperties(TypeClient expectedTypeClient) {
        assertTypeClientAllUpdatablePropertiesEquals(expectedTypeClient, getPersistedTypeClient(expectedTypeClient));
    }
}
