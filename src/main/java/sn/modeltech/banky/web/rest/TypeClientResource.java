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
import sn.modeltech.banky.repository.TypeClientRepository;
import sn.modeltech.banky.service.TypeClientService;
import sn.modeltech.banky.service.dto.TypeClientDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.TypeClient}.
 */
@RestController
@RequestMapping("/api/type-clients")
public class TypeClientResource {

    private static final Logger LOG = LoggerFactory.getLogger(TypeClientResource.class);

    private static final String ENTITY_NAME = "typeClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeClientService typeClientService;

    private final TypeClientRepository typeClientRepository;

    public TypeClientResource(TypeClientService typeClientService, TypeClientRepository typeClientRepository) {
        this.typeClientService = typeClientService;
        this.typeClientRepository = typeClientRepository;
    }

    /**
     * {@code POST  /type-clients} : Create a new typeClient.
     *
     * @param typeClientDTO the typeClientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeClientDTO, or with status {@code 400 (Bad Request)} if the typeClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypeClientDTO> createTypeClient(@RequestBody TypeClientDTO typeClientDTO) throws URISyntaxException {
        LOG.debug("REST request to save TypeClient : {}", typeClientDTO);
        if (typeClientRepository.existsById(typeClientDTO.getIdTypeClient())) {
            throw new BadRequestAlertException("typeClient already exists", ENTITY_NAME, "idexists");
        }
        typeClientDTO = typeClientService.save(typeClientDTO);
        return ResponseEntity.created(new URI("/api/type-clients/" + typeClientDTO.getIdTypeClient()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, typeClientDTO.getIdTypeClient()))
            .body(typeClientDTO);
    }

    /**
     * {@code PUT  /type-clients/:idTypeClient} : Updates an existing typeClient.
     *
     * @param idTypeClient the id of the typeClientDTO to save.
     * @param typeClientDTO the typeClientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeClientDTO,
     * or with status {@code 400 (Bad Request)} if the typeClientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeClientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idTypeClient}")
    public ResponseEntity<TypeClientDTO> updateTypeClient(
        @PathVariable(value = "idTypeClient", required = false) final String idTypeClient,
        @RequestBody TypeClientDTO typeClientDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TypeClient : {}, {}", idTypeClient, typeClientDTO);
        if (typeClientDTO.getIdTypeClient() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idTypeClient, typeClientDTO.getIdTypeClient())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeClientRepository.existsById(idTypeClient)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        typeClientDTO = typeClientService.update(typeClientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeClientDTO.getIdTypeClient()))
            .body(typeClientDTO);
    }

    /**
     * {@code PATCH  /type-clients/:idTypeClient} : Partial updates given fields of an existing typeClient, field will ignore if it is null
     *
     * @param idTypeClient the id of the typeClientDTO to save.
     * @param typeClientDTO the typeClientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeClientDTO,
     * or with status {@code 400 (Bad Request)} if the typeClientDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeClientDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeClientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idTypeClient}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeClientDTO> partialUpdateTypeClient(
        @PathVariable(value = "idTypeClient", required = false) final String idTypeClient,
        @RequestBody TypeClientDTO typeClientDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TypeClient partially : {}, {}", idTypeClient, typeClientDTO);
        if (typeClientDTO.getIdTypeClient() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idTypeClient, typeClientDTO.getIdTypeClient())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeClientRepository.existsById(idTypeClient)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeClientDTO> result = typeClientService.partialUpdate(typeClientDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeClientDTO.getIdTypeClient())
        );
    }

    /**
     * {@code GET  /type-clients} : get all the typeClients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeClients in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TypeClientDTO>> getAllTypeClients(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of TypeClients");
        Page<TypeClientDTO> page = typeClientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-clients/:id} : get the "id" typeClient.
     *
     * @param id the id of the typeClientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeClientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeClientDTO> getTypeClient(@PathVariable("id") String id) {
        LOG.debug("REST request to get TypeClient : {}", id);
        Optional<TypeClientDTO> typeClientDTO = typeClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeClientDTO);
    }

    /**
     * {@code DELETE  /type-clients/:id} : delete the "id" typeClient.
     *
     * @param id the id of the typeClientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeClient(@PathVariable("id") String id) {
        LOG.debug("REST request to delete TypeClient : {}", id);
        typeClientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
