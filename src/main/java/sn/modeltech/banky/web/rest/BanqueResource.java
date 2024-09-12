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
import sn.modeltech.banky.repository.BanqueRepository;
import sn.modeltech.banky.service.BanqueService;
import sn.modeltech.banky.service.dto.BanqueDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.Banque}.
 */
@RestController
@RequestMapping("/api/banques")
public class BanqueResource {

    private static final Logger LOG = LoggerFactory.getLogger(BanqueResource.class);

    private static final String ENTITY_NAME = "banque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BanqueService banqueService;

    private final BanqueRepository banqueRepository;

    public BanqueResource(BanqueService banqueService, BanqueRepository banqueRepository) {
        this.banqueService = banqueService;
        this.banqueRepository = banqueRepository;
    }

    /**
     * {@code POST  /banques} : Create a new banque.
     *
     * @param banqueDTO the banqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banqueDTO, or with status {@code 400 (Bad Request)} if the banque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BanqueDTO> createBanque(@RequestBody BanqueDTO banqueDTO) throws URISyntaxException {
        LOG.debug("REST request to save Banque : {}", banqueDTO);
        if (banqueRepository.existsById(banqueDTO.getIdBanque())) {
            throw new BadRequestAlertException("banque already exists", ENTITY_NAME, "idexists");
        }
        banqueDTO = banqueService.save(banqueDTO);
        return ResponseEntity.created(new URI("/api/banques/" + banqueDTO.getIdBanque()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, banqueDTO.getIdBanque()))
            .body(banqueDTO);
    }

    /**
     * {@code PUT  /banques/:idBanque} : Updates an existing banque.
     *
     * @param idBanque the id of the banqueDTO to save.
     * @param banqueDTO the banqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banqueDTO,
     * or with status {@code 400 (Bad Request)} if the banqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idBanque}")
    public ResponseEntity<BanqueDTO> updateBanque(
        @PathVariable(value = "idBanque", required = false) final String idBanque,
        @RequestBody BanqueDTO banqueDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Banque : {}, {}", idBanque, banqueDTO);
        if (banqueDTO.getIdBanque() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idBanque, banqueDTO.getIdBanque())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!banqueRepository.existsById(idBanque)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        banqueDTO = banqueService.update(banqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banqueDTO.getIdBanque()))
            .body(banqueDTO);
    }

    /**
     * {@code PATCH  /banques/:idBanque} : Partial updates given fields of an existing banque, field will ignore if it is null
     *
     * @param idBanque the id of the banqueDTO to save.
     * @param banqueDTO the banqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banqueDTO,
     * or with status {@code 400 (Bad Request)} if the banqueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the banqueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the banqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idBanque}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BanqueDTO> partialUpdateBanque(
        @PathVariable(value = "idBanque", required = false) final String idBanque,
        @RequestBody BanqueDTO banqueDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Banque partially : {}, {}", idBanque, banqueDTO);
        if (banqueDTO.getIdBanque() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idBanque, banqueDTO.getIdBanque())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!banqueRepository.existsById(idBanque)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BanqueDTO> result = banqueService.partialUpdate(banqueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banqueDTO.getIdBanque())
        );
    }

    /**
     * {@code GET  /banques} : get all the banques.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banques in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BanqueDTO>> getAllBanques(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Banques");
        Page<BanqueDTO> page = banqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banques/:id} : get the "id" banque.
     *
     * @param id the id of the banqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BanqueDTO> getBanque(@PathVariable("id") String id) {
        LOG.debug("REST request to get Banque : {}", id);
        Optional<BanqueDTO> banqueDTO = banqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banqueDTO);
    }

    /**
     * {@code DELETE  /banques/:id} : delete the "id" banque.
     *
     * @param id the id of the banqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanque(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Banque : {}", id);
        banqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
