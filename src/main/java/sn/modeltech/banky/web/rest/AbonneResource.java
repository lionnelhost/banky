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
import sn.modeltech.banky.repository.AbonneRepository;
import sn.modeltech.banky.service.AbonneService;
import sn.modeltech.banky.service.dto.AbonneDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.Abonne}.
 */
@RestController
@RequestMapping("/api/abonnes")
public class AbonneResource {

    private static final Logger LOG = LoggerFactory.getLogger(AbonneResource.class);

    private static final String ENTITY_NAME = "abonne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbonneService abonneService;

    private final AbonneRepository abonneRepository;

    public AbonneResource(AbonneService abonneService, AbonneRepository abonneRepository) {
        this.abonneService = abonneService;
        this.abonneRepository = abonneRepository;
    }

    /**
     * {@code POST  /abonnes} : Create a new abonne.
     *
     * @param abonneDTO the abonneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new abonneDTO, or with status {@code 400 (Bad Request)} if the abonne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AbonneDTO> createAbonne(@RequestBody AbonneDTO abonneDTO) throws URISyntaxException {
        LOG.debug("REST request to save Abonne : {}", abonneDTO);
        if (abonneRepository.existsById(abonneDTO.getIdAbonne())) {
            throw new BadRequestAlertException("abonne already exists", ENTITY_NAME, "idexists");
        }
        abonneDTO = abonneService.save(abonneDTO);
        return ResponseEntity.created(new URI("/api/abonnes/" + abonneDTO.getIdAbonne()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, abonneDTO.getIdAbonne()))
            .body(abonneDTO);
    }

    /**
     * {@code PUT  /abonnes/:idAbonne} : Updates an existing abonne.
     *
     * @param idAbonne the id of the abonneDTO to save.
     * @param abonneDTO the abonneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abonneDTO,
     * or with status {@code 400 (Bad Request)} if the abonneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the abonneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idAbonne}")
    public ResponseEntity<AbonneDTO> updateAbonne(
        @PathVariable(value = "idAbonne", required = false) final String idAbonne,
        @RequestBody AbonneDTO abonneDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Abonne : {}, {}", idAbonne, abonneDTO);
        if (abonneDTO.getIdAbonne() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idAbonne, abonneDTO.getIdAbonne())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abonneRepository.existsById(idAbonne)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        abonneDTO = abonneService.update(abonneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abonneDTO.getIdAbonne()))
            .body(abonneDTO);
    }

    /**
     * {@code PATCH  /abonnes/:idAbonne} : Partial updates given fields of an existing abonne, field will ignore if it is null
     *
     * @param idAbonne the id of the abonneDTO to save.
     * @param abonneDTO the abonneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abonneDTO,
     * or with status {@code 400 (Bad Request)} if the abonneDTO is not valid,
     * or with status {@code 404 (Not Found)} if the abonneDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the abonneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idAbonne}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AbonneDTO> partialUpdateAbonne(
        @PathVariable(value = "idAbonne", required = false) final String idAbonne,
        @RequestBody AbonneDTO abonneDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Abonne partially : {}, {}", idAbonne, abonneDTO);
        if (abonneDTO.getIdAbonne() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idAbonne, abonneDTO.getIdAbonne())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abonneRepository.existsById(idAbonne)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AbonneDTO> result = abonneService.partialUpdate(abonneDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abonneDTO.getIdAbonne())
        );
    }

    /**
     * {@code GET  /abonnes} : get all the abonnes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of abonnes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AbonneDTO>> getAllAbonnes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Abonnes");
        Page<AbonneDTO> page = abonneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /abonnes/:id} : get the "id" abonne.
     *
     * @param id the id of the abonneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the abonneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AbonneDTO> getAbonne(@PathVariable("id") String id) {
        LOG.debug("REST request to get Abonne : {}", id);
        Optional<AbonneDTO> abonneDTO = abonneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(abonneDTO);
    }

    /**
     * {@code DELETE  /abonnes/:id} : delete the "id" abonne.
     *
     * @param id the id of the abonneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbonne(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Abonne : {}", id);
        abonneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
