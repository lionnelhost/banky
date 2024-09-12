import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Participant e2e test', () => {
  const participantPageUrl = '/participant';
  const participantPageUrlPattern = new RegExp('/participant(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const participantSample = {};

  let participant;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/participants+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/participants').as('postEntityRequest');
    cy.intercept('DELETE', '/api/participants/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (participant) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/participants/${participant.idParticipant}`,
      }).then(() => {
        participant = undefined;
      });
    }
  });

  it('Participants menu should load Participants page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('participant');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Participant').should('exist');
    cy.url().should('match', participantPageUrlPattern);
  });

  describe('Participant page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(participantPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Participant page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/participant/new$'));
        cy.getEntityCreateUpdateHeading('Participant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', participantPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/participants',
          body: participantSample,
        }).then(({ body }) => {
          participant = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/participants+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/participants?page=0&size=20>; rel="last",<http://localhost/api/participants?page=0&size=20>; rel="first"',
              },
              body: [participant],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(participantPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Participant page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('participant');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', participantPageUrlPattern);
      });

      it('edit button click should load edit Participant page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Participant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', participantPageUrlPattern);
      });

      it('edit button click should load edit Participant page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Participant');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', participantPageUrlPattern);
      });

      it('last delete button click should delete instance of Participant', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('participant').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', participantPageUrlPattern);

        participant = undefined;
      });
    });
  });

  describe('new Participant page', () => {
    beforeEach(() => {
      cy.visit(`${participantPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Participant');
    });

    it('should create an instance of Participant', () => {
      cy.get(`[data-cy="idParticipant"]`).type('89e0417e-d8d6-42a9-b11d-e3d0ffd50205');
      cy.get(`[data-cy="idParticipant"]`).should('have.value', '89e0417e-d8d6-42a9-b11d-e3d0ffd50205');

      cy.get(`[data-cy="codeParticipant"]`).type('disparaître');
      cy.get(`[data-cy="codeParticipant"]`).should('have.value', 'disparaître');

      cy.get(`[data-cy="codeBanque"]`).type('hirsute pourpre changer');
      cy.get(`[data-cy="codeBanque"]`).should('have.value', 'hirsute pourpre changer');

      cy.get(`[data-cy="nomBanque"]`).type('chef');
      cy.get(`[data-cy="nomBanque"]`).should('have.value', 'chef');

      cy.get(`[data-cy="libelle"]`).type('de manière à ce que');
      cy.get(`[data-cy="libelle"]`).should('have.value', 'de manière à ce que');

      cy.get(`[data-cy="pays"]`).type('suivant badaboum lectorat');
      cy.get(`[data-cy="pays"]`).should('have.value', 'suivant badaboum lectorat');

      cy.get(`[data-cy="isActif"]`).should('not.be.checked');
      cy.get(`[data-cy="isActif"]`).click();
      cy.get(`[data-cy="isActif"]`).should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        participant = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', participantPageUrlPattern);
    });
  });
});
