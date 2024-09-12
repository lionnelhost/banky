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

describe('CompteBancaire e2e test', () => {
  const compteBancairePageUrl = '/compte-bancaire';
  const compteBancairePageUrlPattern = new RegExp('/compte-bancaire(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const compteBancaireSample = {};

  let compteBancaire;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/compte-bancaires+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/compte-bancaires').as('postEntityRequest');
    cy.intercept('DELETE', '/api/compte-bancaires/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (compteBancaire) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/compte-bancaires/${compteBancaire.idCompteBancaire}`,
      }).then(() => {
        compteBancaire = undefined;
      });
    }
  });

  it('CompteBancaires menu should load CompteBancaires page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('compte-bancaire');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CompteBancaire').should('exist');
    cy.url().should('match', compteBancairePageUrlPattern);
  });

  describe('CompteBancaire page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(compteBancairePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CompteBancaire page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/compte-bancaire/new$'));
        cy.getEntityCreateUpdateHeading('CompteBancaire');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', compteBancairePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/compte-bancaires',
          body: compteBancaireSample,
        }).then(({ body }) => {
          compteBancaire = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/compte-bancaires+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/compte-bancaires?page=0&size=20>; rel="last",<http://localhost/api/compte-bancaires?page=0&size=20>; rel="first"',
              },
              body: [compteBancaire],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(compteBancairePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CompteBancaire page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('compteBancaire');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', compteBancairePageUrlPattern);
      });

      it('edit button click should load edit CompteBancaire page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompteBancaire');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', compteBancairePageUrlPattern);
      });

      it('edit button click should load edit CompteBancaire page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompteBancaire');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', compteBancairePageUrlPattern);
      });

      it('last delete button click should delete instance of CompteBancaire', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('compteBancaire').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', compteBancairePageUrlPattern);

        compteBancaire = undefined;
      });
    });
  });

  describe('new CompteBancaire page', () => {
    beforeEach(() => {
      cy.visit(`${compteBancairePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CompteBancaire');
    });

    it('should create an instance of CompteBancaire', () => {
      cy.get(`[data-cy="idCompteBancaire"]`).type('554a5f45-dd00-4b7e-8b31-3f39157f506f');
      cy.get(`[data-cy="idCompteBancaire"]`).should('have.value', '554a5f45-dd00-4b7e-8b31-3f39157f506f');

      cy.get(`[data-cy="age"]`).type('en faveur de super');
      cy.get(`[data-cy="age"]`).should('have.value', 'en faveur de super');

      cy.get(`[data-cy="ncp"]`).type('si bien que parlementaire');
      cy.get(`[data-cy="ncp"]`).should('have.value', 'si bien que parlementaire');

      cy.get(`[data-cy="sde"]`).type('gérer mélancolique quand');
      cy.get(`[data-cy="sde"]`).should('have.value', 'gérer mélancolique quand');

      cy.get(`[data-cy="sin"]`).type('conseil municipal');
      cy.get(`[data-cy="sin"]`).should('have.value', 'conseil municipal');

      cy.get(`[data-cy="soldeDisponible"]`).type('malgré taire un peu');
      cy.get(`[data-cy="soldeDisponible"]`).should('have.value', 'malgré taire un peu');

      cy.get(`[data-cy="rib"]`).type('épanouir puisque sombre');
      cy.get(`[data-cy="rib"]`).should('have.value', 'épanouir puisque sombre');

      cy.get(`[data-cy="status"]`).select('INACTIF');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        compteBancaire = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', compteBancairePageUrlPattern);
    });
  });
});
