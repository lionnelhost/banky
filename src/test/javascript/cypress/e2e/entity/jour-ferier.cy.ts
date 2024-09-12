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

describe('JourFerier e2e test', () => {
  const jourFerierPageUrl = '/jour-ferier';
  const jourFerierPageUrlPattern = new RegExp('/jour-ferier(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const jourFerierSample = {};

  let jourFerier;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/jour-feriers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/jour-feriers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/jour-feriers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (jourFerier) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/jour-feriers/${jourFerier.idJourFerie}`,
      }).then(() => {
        jourFerier = undefined;
      });
    }
  });

  it('JourFeriers menu should load JourFeriers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('jour-ferier');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('JourFerier').should('exist');
    cy.url().should('match', jourFerierPageUrlPattern);
  });

  describe('JourFerier page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(jourFerierPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create JourFerier page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/jour-ferier/new$'));
        cy.getEntityCreateUpdateHeading('JourFerier');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jourFerierPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/jour-feriers',
          body: jourFerierSample,
        }).then(({ body }) => {
          jourFerier = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/jour-feriers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/jour-feriers?page=0&size=20>; rel="last",<http://localhost/api/jour-feriers?page=0&size=20>; rel="first"',
              },
              body: [jourFerier],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(jourFerierPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details JourFerier page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('jourFerier');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jourFerierPageUrlPattern);
      });

      it('edit button click should load edit JourFerier page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('JourFerier');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jourFerierPageUrlPattern);
      });

      it('edit button click should load edit JourFerier page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('JourFerier');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jourFerierPageUrlPattern);
      });

      it('last delete button click should delete instance of JourFerier', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('jourFerier').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jourFerierPageUrlPattern);

        jourFerier = undefined;
      });
    });
  });

  describe('new JourFerier page', () => {
    beforeEach(() => {
      cy.visit(`${jourFerierPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('JourFerier');
    });

    it('should create an instance of JourFerier', () => {
      cy.get(`[data-cy="idJourFerie"]`).type('7afef4a5-1fc0-4897-9718-149d5d47158f');
      cy.get(`[data-cy="idJourFerie"]`).should('have.value', '7afef4a5-1fc0-4897-9718-149d5d47158f');

      cy.get(`[data-cy="libelle"]`).type('chef');
      cy.get(`[data-cy="libelle"]`).should('have.value', 'chef');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        jourFerier = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', jourFerierPageUrlPattern);
    });
  });
});
