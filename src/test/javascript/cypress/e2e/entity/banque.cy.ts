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

describe('Banque e2e test', () => {
  const banquePageUrl = '/banque';
  const banquePageUrlPattern = new RegExp('/banque(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const banqueSample = {};

  let banque;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/banques+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/banques').as('postEntityRequest');
    cy.intercept('DELETE', '/api/banques/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (banque) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/banques/${banque.idBanque}`,
      }).then(() => {
        banque = undefined;
      });
    }
  });

  it('Banques menu should load Banques page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('banque');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Banque').should('exist');
    cy.url().should('match', banquePageUrlPattern);
  });

  describe('Banque page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(banquePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Banque page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/banque/new$'));
        cy.getEntityCreateUpdateHeading('Banque');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', banquePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/banques',
          body: banqueSample,
        }).then(({ body }) => {
          banque = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/banques+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/banques?page=0&size=20>; rel="last",<http://localhost/api/banques?page=0&size=20>; rel="first"',
              },
              body: [banque],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(banquePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Banque page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('banque');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', banquePageUrlPattern);
      });

      it('edit button click should load edit Banque page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Banque');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', banquePageUrlPattern);
      });

      it('edit button click should load edit Banque page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Banque');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', banquePageUrlPattern);
      });

      it('last delete button click should delete instance of Banque', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('banque').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', banquePageUrlPattern);

        banque = undefined;
      });
    });
  });

  describe('new Banque page', () => {
    beforeEach(() => {
      cy.visit(`${banquePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Banque');
    });

    it('should create an instance of Banque', () => {
      cy.get(`[data-cy="idBanque"]`).type('9ec94974-bb02-46f5-8383-f9113de877f6');
      cy.get(`[data-cy="idBanque"]`).should('have.value', '9ec94974-bb02-46f5-8383-f9113de877f6');

      cy.get(`[data-cy="code"]`).type('bien que bien');
      cy.get(`[data-cy="code"]`).should('have.value', 'bien que bien');

      cy.get(`[data-cy="devise"]`).select('XAF');

      cy.get(`[data-cy="langue"]`).select('FR');

      cy.get(`[data-cy="logo"]`).type('en face de carrément');
      cy.get(`[data-cy="logo"]`).should('have.value', 'en face de carrément');

      cy.get(`[data-cy="codeSwift"]`).type('approximativement de façon à');
      cy.get(`[data-cy="codeSwift"]`).should('have.value', 'approximativement de façon à');

      cy.get(`[data-cy="codeIban"]`).type('lors gens sous');
      cy.get(`[data-cy="codeIban"]`).should('have.value', 'lors gens sous');

      cy.get(`[data-cy="codePays"]`).type('magnifique au lieu de areu areu');
      cy.get(`[data-cy="codePays"]`).should('have.value', 'magnifique au lieu de areu areu');

      cy.get(`[data-cy="fuseauHoraire"]`).type('adversaire');
      cy.get(`[data-cy="fuseauHoraire"]`).should('have.value', 'adversaire');

      cy.get(`[data-cy="cutOffTime"]`).type('à seule fin de refermer');
      cy.get(`[data-cy="cutOffTime"]`).should('have.value', 'à seule fin de refermer');

      cy.get(`[data-cy="codeParticipant"]`).type('probablement racheter conformer');
      cy.get(`[data-cy="codeParticipant"]`).should('have.value', 'probablement racheter conformer');

      cy.get(`[data-cy="libelleParticipant"]`).type('avant que plic ensemble');
      cy.get(`[data-cy="libelleParticipant"]`).should('have.value', 'avant que plic ensemble');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        banque = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', banquePageUrlPattern);
    });
  });
});
