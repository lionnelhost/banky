package sn.modeltech.banky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sn.modeltech.banky.domain.TypeTransactionAsserts.*;
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
import sn.modeltech.banky.domain.TypeTransaction;
import sn.modeltech.banky.repository.TypeTransactionRepository;
import sn.modeltech.banky.service.dto.TypeTransactionDTO;
import sn.modeltech.banky.service.mapper.TypeTransactionMapper;

/**
 * Integration tests for the {@link TypeTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeTransactionResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idTypeTransaction}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeTransactionRepository typeTransactionRepository;

    @Autowired
    private TypeTransactionMapper typeTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeTransactionMockMvc;

    private TypeTransaction typeTransaction;

    private TypeTransaction insertedTypeTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeTransaction createEntity() {
        return new TypeTransaction().idTypeTransaction(UUID.randomUUID().toString()).libelle(DEFAULT_LIBELLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeTransaction createUpdatedEntity() {
        return new TypeTransaction().idTypeTransaction(UUID.randomUUID().toString()).libelle(UPDATED_LIBELLE);
    }

    @BeforeEach
    public void initTest() {
        typeTransaction = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeTransaction != null) {
            typeTransactionRepository.delete(insertedTypeTransaction);
            insertedTypeTransaction = null;
        }
    }

    @Test
    @Transactional
    void createTypeTransaction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeTransaction
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(typeTransaction);
        var returnedTypeTransactionDTO = om.readValue(
            restTypeTransactionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(typeTransactionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeTransactionDTO.class
        );

        // Validate the TypeTransaction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeTransaction = typeTransactionMapper.toEntity(returnedTypeTransactionDTO);
        assertTypeTransactionUpdatableFieldsEquals(returnedTypeTransaction, getPersistedTypeTransaction(returnedTypeTransaction));

        insertedTypeTransaction = returnedTypeTransaction;
    }

    @Test
    @Transactional
    void createTypeTransactionWithExistingId() throws Exception {
        // Create the TypeTransaction with an existing ID
        insertedTypeTransaction = typeTransactionRepository.saveAndFlush(typeTransaction);
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(typeTransaction);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeTransactions() throws Exception {
        // Initialize the database
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());
        insertedTypeTransaction = typeTransactionRepository.saveAndFlush(typeTransaction);

        // Get all the typeTransactionList
        restTypeTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idTypeTransaction,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idTypeTransaction").value(hasItem(typeTransaction.getIdTypeTransaction())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getTypeTransaction() throws Exception {
        // Initialize the database
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());
        insertedTypeTransaction = typeTransactionRepository.saveAndFlush(typeTransaction);

        // Get the typeTransaction
        restTypeTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, typeTransaction.getIdTypeTransaction()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idTypeTransaction").value(typeTransaction.getIdTypeTransaction()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingTypeTransaction() throws Exception {
        // Get the typeTransaction
        restTypeTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeTransaction() throws Exception {
        // Initialize the database
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());
        insertedTypeTransaction = typeTransactionRepository.saveAndFlush(typeTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeTransaction
        TypeTransaction updatedTypeTransaction = typeTransactionRepository.findById(typeTransaction.getIdTypeTransaction()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeTransaction are not directly saved in db
        em.detach(updatedTypeTransaction);
        updatedTypeTransaction.libelle(UPDATED_LIBELLE);
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(updatedTypeTransaction);

        restTypeTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeTransactionDTO.getIdTypeTransaction())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeTransactionToMatchAllProperties(updatedTypeTransaction);
    }

    @Test
    @Transactional
    void putNonExistingTypeTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());

        // Create the TypeTransaction
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(typeTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeTransactionDTO.getIdTypeTransaction())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());

        // Create the TypeTransaction
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(typeTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());

        // Create the TypeTransaction
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(typeTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeTransactionMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeTransactionWithPatch() throws Exception {
        // Initialize the database
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());
        insertedTypeTransaction = typeTransactionRepository.saveAndFlush(typeTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeTransaction using partial update
        TypeTransaction partialUpdatedTypeTransaction = new TypeTransaction();
        partialUpdatedTypeTransaction.setIdTypeTransaction(typeTransaction.getIdTypeTransaction());

        restTypeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeTransaction.getIdTypeTransaction())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeTransaction))
            )
            .andExpect(status().isOk());

        // Validate the TypeTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeTransactionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeTransaction, typeTransaction),
            getPersistedTypeTransaction(typeTransaction)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeTransactionWithPatch() throws Exception {
        // Initialize the database
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());
        insertedTypeTransaction = typeTransactionRepository.saveAndFlush(typeTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeTransaction using partial update
        TypeTransaction partialUpdatedTypeTransaction = new TypeTransaction();
        partialUpdatedTypeTransaction.setIdTypeTransaction(typeTransaction.getIdTypeTransaction());

        partialUpdatedTypeTransaction.libelle(UPDATED_LIBELLE);

        restTypeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeTransaction.getIdTypeTransaction())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeTransaction))
            )
            .andExpect(status().isOk());

        // Validate the TypeTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeTransactionUpdatableFieldsEquals(
            partialUpdatedTypeTransaction,
            getPersistedTypeTransaction(partialUpdatedTypeTransaction)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTypeTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());

        // Create the TypeTransaction
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(typeTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeTransactionDTO.getIdTypeTransaction())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());

        // Create the TypeTransaction
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(typeTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());

        // Create the TypeTransaction
        TypeTransactionDTO typeTransactionDTO = typeTransactionMapper.toDto(typeTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeTransaction() throws Exception {
        // Initialize the database
        typeTransaction.setIdTypeTransaction(UUID.randomUUID().toString());
        insertedTypeTransaction = typeTransactionRepository.saveAndFlush(typeTransaction);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeTransaction
        restTypeTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeTransaction.getIdTypeTransaction()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeTransactionRepository.count();
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

    protected TypeTransaction getPersistedTypeTransaction(TypeTransaction typeTransaction) {
        return typeTransactionRepository.findById(typeTransaction.getIdTypeTransaction()).orElseThrow();
    }

    protected void assertPersistedTypeTransactionToMatchAllProperties(TypeTransaction expectedTypeTransaction) {
        assertTypeTransactionAllPropertiesEquals(expectedTypeTransaction, getPersistedTypeTransaction(expectedTypeTransaction));
    }

    protected void assertPersistedTypeTransactionToMatchUpdatableProperties(TypeTransaction expectedTypeTransaction) {
        assertTypeTransactionAllUpdatablePropertiesEquals(expectedTypeTransaction, getPersistedTypeTransaction(expectedTypeTransaction));
    }
}
