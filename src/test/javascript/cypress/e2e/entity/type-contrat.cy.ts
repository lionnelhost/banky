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

describe('TypeContrat e2e test', () => {
  const typeContratPageUrl = '/type-contrat';
  const typeContratPageUrlPattern = new RegExp('/type-contrat(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const typeContratSample = {};

  let typeContrat;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/type-contrats+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/type-contrats').as('postEntityRequest');
    cy.intercept('DELETE', '/api/type-contrats/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (typeContrat) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-contrats/${typeContrat.idTypeContrat}`,
      }).then(() => {
        typeContrat = undefined;
      });
    }
  });

  it('TypeContrats menu should load TypeContrats page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('type-contrat');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TypeContrat').should('exist');
    cy.url().should('match', typeContratPageUrlPattern);
  });

  describe('TypeContrat page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(typeContratPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TypeContrat page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/type-contrat/new$'));
        cy.getEntityCreateUpdateHeading('TypeContrat');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeContratPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/type-contrats',
          body: typeContratSample,
        }).then(({ body }) => {
          typeContrat = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/type-contrats+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/type-contrats?page=0&size=20>; rel="last",<http://localhost/api/type-contrats?page=0&size=20>; rel="first"',
              },
              body: [typeContrat],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(typeContratPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TypeContrat page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('typeContrat');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeContratPageUrlPattern);
      });

      it('edit button click should load edit TypeContrat page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeContrat');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeContratPageUrlPattern);
      });

      it('edit button click should load edit TypeContrat page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeContrat');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeContratPageUrlPattern);
      });

      it('last delete button click should delete instance of TypeContrat', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('typeContrat').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeContratPageUrlPattern);

        typeContrat = undefined;
      });
    });
  });

  describe('new TypeContrat page', () => {
    beforeEach(() => {
      cy.visit(`${typeContratPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TypeContrat');
    });

    it('should create an instance of TypeContrat', () => {
      cy.get(`[data-cy="idTypeContrat"]`).type('1bbd0b47-3dcf-41bd-b086-0ff062da697b');
      cy.get(`[data-cy="idTypeContrat"]`).should('have.value', '1bbd0b47-3dcf-41bd-b086-0ff062da697b');

      cy.get(`[data-cy="libelle"]`).type('de façon à présidence dès');
      cy.get(`[data-cy="libelle"]`).should('have.value', 'de façon à présidence dès');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        typeContrat = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', typeContratPageUrlPattern);
    });
  });
});
