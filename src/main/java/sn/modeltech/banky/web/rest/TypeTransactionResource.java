package sn.modeltech.banky.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.modeltech.banky.repository.TypeTransactionRepository;
import sn.modeltech.banky.service.TypeTransactionService;
import sn.modeltech.banky.service.dto.TypeTransactionDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.TypeTransaction}.
 */
@RestController
@RequestMapping("/api/type-transactions")
public class TypeTransactionResource {

    private static final Logger LOG = LoggerFactory.getLogger(TypeTransactionResource.class);

    private static final String ENTITY_NAME = "typeTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeTransactionService typeTransactionService;

    private final TypeTransactionRepository typeTransactionRepository;

    public TypeTransactionResource(TypeTransactionService typeTransactionService, TypeTransactionRepository typeTransactionRepository) {
        this.typeTransactionService = typeTransactionService;
        this.typeTransactionRepository = typeTransactionRepository;
    }

    /**
     * {@code POST  /type-transactions} : Create a new typeTransaction.
     *
     * @param typeTransactionDTO the typeTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeTransactionDTO, or with status {@code 400 (Bad Request)} if the typeTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypeTransactionDTO> createTypeTransaction(@RequestBody TypeTransactionDTO typeTransactionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save TypeTransaction : {}", typeTransactionDTO);
        if (typeTransactionRepository.existsById(typeTransactionDTO.getIdTypeTransaction())) {
            throw new BadRequestAlertException("typeTransaction already exists", ENTITY_NAME, "idexists");
        }
        typeTransactionDTO = typeTransactionService.save(typeTransactionDTO);
        return ResponseEntity.created(new URI("/api/type-transactions/" + typeTransactionDTO.getIdTypeTransaction()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, typeTransactionDTO.getIdTypeTransaction()))
            .body(typeTransactionDTO);
    }

    /**
     * {@code PUT  /type-transactions/:idTypeTransaction} : Updates an existing typeTransaction.
     *
     * @param idTypeTransaction the id of the typeTransactionDTO to save.
     * @param typeTransactionDTO the typeTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the typeTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idTypeTransaction}")
    public ResponseEntity<TypeTransactionDTO> updateTypeTransaction(
        @PathVariable(value = "idTypeTransaction", required = false) final String idTypeTransaction,
        @RequestBody TypeTransactionDTO typeTransactionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TypeTransaction : {}, {}", idTypeTransaction, typeTransactionDTO);
        if (typeTransactionDTO.getIdTypeTransaction() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idTypeTransaction, typeTransactionDTO.getIdTypeTransaction())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeTransactionRepository.existsById(idTypeTransaction)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        typeTransactionDTO = typeTransactionService.update(typeTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeTransactionDTO.getIdTypeTransaction()))
            .body(typeTransactionDTO);
    }

    /**
     * {@code PATCH  /type-transactions/:idTypeTransaction} : Partial updates given fields of an existing typeTransaction, field will ignore if it is null
     *
     * @param idTypeTransaction the id of the typeTransactionDTO to save.
     * @param typeTransactionDTO the typeTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the typeTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idTypeTransaction}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeTransactionDTO> partialUpdateTypeTransaction(
        @PathVariable(value = "idTypeTransaction", required = false) final String idTypeTransaction,
        @RequestBody TypeTransactionDTO typeTransactionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TypeTransaction partially : {}, {}", idTypeTransaction, typeTransactionDTO);
        if (typeTransactionDTO.getIdTypeTransaction() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idTypeTransaction, typeTransactionDTO.getIdTypeTransaction())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeTransactionRepository.existsById(idTypeTransaction)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeTransactionDTO> result = typeTransactionService.partialUpdate(typeTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeTransactionDTO.getIdTypeTransaction())
        );
    }

    /**
     * {@code GET  /type-transactions} : get all the typeTransactions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeTransactions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TypeTransactionDTO>> getAllTypeTransactions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of TypeTransactions");
        Page<TypeTransactionDTO> page = typeTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-transactions/:id} : get the "id" typeTransaction.
     *
     * @param id the id of the typeTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeTransactionDTO> getTypeTransaction(@PathVariable("id") String id) {
        LOG.debug("REST request to get TypeTransaction : {}", id);
        Optional<TypeTransactionDTO> typeTransactionDTO = typeTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeTransactionDTO);
    }

    /**
     * {@code DELETE  /type-transactions/:id} : delete the "id" typeTransaction.
     *
     * @param id the id of the typeTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeTransaction(@PathVariable("id") String id) {
        LOG.debug("REST request to delete TypeTransaction : {}", id);
        typeTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
