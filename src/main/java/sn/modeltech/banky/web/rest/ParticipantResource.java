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
import sn.modeltech.banky.repository.ParticipantRepository;
import sn.modeltech.banky.service.ParticipantService;
import sn.modeltech.banky.service.dto.ParticipantDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.Participant}.
 */
@RestController
@RequestMapping("/api/participants")
public class ParticipantResource {

    private static final Logger LOG = LoggerFactory.getLogger(ParticipantResource.class);

    private static final String ENTITY_NAME = "participant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipantService participantService;

    private final ParticipantRepository participantRepository;

    public ParticipantResource(ParticipantService participantService, ParticipantRepository participantRepository) {
        this.participantService = participantService;
        this.participantRepository = participantRepository;
    }

    /**
     * {@code POST  /participants} : Create a new participant.
     *
     * @param participantDTO the participantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participantDTO, or with status {@code 400 (Bad Request)} if the participant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO participantDTO) throws URISyntaxException {
        LOG.debug("REST request to save Participant : {}", participantDTO);
        if (participantRepository.existsById(participantDTO.getIdParticipant())) {
            throw new BadRequestAlertException("participant already exists", ENTITY_NAME, "idexists");
        }
        participantDTO = participantService.save(participantDTO);
        return ResponseEntity.created(new URI("/api/participants/" + participantDTO.getIdParticipant()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, participantDTO.getIdParticipant()))
            .body(participantDTO);
    }

    /**
     * {@code PUT  /participants/:idParticipant} : Updates an existing participant.
     *
     * @param idParticipant the id of the participantDTO to save.
     * @param participantDTO the participantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participantDTO,
     * or with status {@code 400 (Bad Request)} if the participantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idParticipant}")
    public ResponseEntity<ParticipantDTO> updateParticipant(
        @PathVariable(value = "idParticipant", required = false) final String idParticipant,
        @RequestBody ParticipantDTO participantDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Participant : {}, {}", idParticipant, participantDTO);
        if (participantDTO.getIdParticipant() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idParticipant, participantDTO.getIdParticipant())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantRepository.existsById(idParticipant)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        participantDTO = participantService.update(participantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participantDTO.getIdParticipant()))
            .body(participantDTO);
    }

    /**
     * {@code PATCH  /participants/:idParticipant} : Partial updates given fields of an existing participant, field will ignore if it is null
     *
     * @param idParticipant the id of the participantDTO to save.
     * @param participantDTO the participantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participantDTO,
     * or with status {@code 400 (Bad Request)} if the participantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the participantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the participantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idParticipant}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParticipantDTO> partialUpdateParticipant(
        @PathVariable(value = "idParticipant", required = false) final String idParticipant,
        @RequestBody ParticipantDTO participantDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Participant partially : {}, {}", idParticipant, participantDTO);
        if (participantDTO.getIdParticipant() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idParticipant, participantDTO.getIdParticipant())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantRepository.existsById(idParticipant)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParticipantDTO> result = participantService.partialUpdate(participantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participantDTO.getIdParticipant())
        );
    }

    /**
     * {@code GET  /participants} : get all the participants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participants in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Participants");
        Page<ParticipantDTO> page = participantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /participants/:id} : get the "id" participant.
     *
     * @param id the id of the participantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipant(@PathVariable("id") String id) {
        LOG.debug("REST request to get Participant : {}", id);
        Optional<ParticipantDTO> participantDTO = participantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participantDTO);
    }

    /**
     * {@code DELETE  /participants/:id} : delete the "id" participant.
     *
     * @param id the id of the participantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Participant : {}", id);
        participantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
