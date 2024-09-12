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

describe('Canal e2e test', () => {
  const canalPageUrl = '/canal';
  const canalPageUrlPattern = new RegExp('/canal(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const canalSample = {};

  let canal;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/canals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/canals').as('postEntityRequest');
    cy.intercept('DELETE', '/api/canals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (canal) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/canals/${canal.idCanal}`,
      }).then(() => {
        canal = undefined;
      });
    }
  });

  it('Canals menu should load Canals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('canal');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Canal').should('exist');
    cy.url().should('match', canalPageUrlPattern);
  });

  describe('Canal page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(canalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Canal page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/canal/new$'));
        cy.getEntityCreateUpdateHeading('Canal');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', canalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/canals',
          body: canalSample,
        }).then(({ body }) => {
          canal = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/canals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/canals?page=0&size=20>; rel="last",<http://localhost/api/canals?page=0&size=20>; rel="first"',
              },
              body: [canal],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(canalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Canal page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('canal');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', canalPageUrlPattern);
      });

      it('edit button click should load edit Canal page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Canal');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', canalPageUrlPattern);
      });

      it('edit button click should load edit Canal page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Canal');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', canalPageUrlPattern);
      });

      it('last delete button click should delete instance of Canal', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('canal').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', canalPageUrlPattern);

        canal = undefined;
      });
    });
  });

  describe('new Canal page', () => {
    beforeEach(() => {
      cy.visit(`${canalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Canal');
    });

    it('should create an instance of Canal', () => {
      cy.get(`[data-cy="idCanal"]`).type('26b3fe10-e2cd-4f86-a835-ee680bf82021');
      cy.get(`[data-cy="idCanal"]`).should('have.value', '26b3fe10-e2cd-4f86-a835-ee680bf82021');

      cy.get(`[data-cy="libelle"]`).type('crier');
      cy.get(`[data-cy="libelle"]`).should('have.value', 'crier');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        canal = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', canalPageUrlPattern);
    });
  });
});
