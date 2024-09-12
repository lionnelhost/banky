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

describe('ParametrageGlobal e2e test', () => {
  const parametrageGlobalPageUrl = '/parametrage-global';
  const parametrageGlobalPageUrlPattern = new RegExp('/parametrage-global(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const parametrageGlobalSample = {};

  let parametrageGlobal;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/parametrage-globals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/parametrage-globals').as('postEntityRequest');
    cy.intercept('DELETE', '/api/parametrage-globals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (parametrageGlobal) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/parametrage-globals/${parametrageGlobal.idParamGlobal}`,
      }).then(() => {
        parametrageGlobal = undefined;
      });
    }
  });

  it('ParametrageGlobals menu should load ParametrageGlobals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('parametrage-global');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ParametrageGlobal').should('exist');
    cy.url().should('match', parametrageGlobalPageUrlPattern);
  });

  describe('ParametrageGlobal page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(parametrageGlobalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ParametrageGlobal page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/parametrage-global/new$'));
        cy.getEntityCreateUpdateHeading('ParametrageGlobal');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageGlobalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/parametrage-globals',
          body: parametrageGlobalSample,
        }).then(({ body }) => {
          parametrageGlobal = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/parametrage-globals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/parametrage-globals?page=0&size=20>; rel="last",<http://localhost/api/parametrage-globals?page=0&size=20>; rel="first"',
              },
              body: [parametrageGlobal],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(parametrageGlobalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ParametrageGlobal page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('parametrageGlobal');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageGlobalPageUrlPattern);
      });

      it('edit button click should load edit ParametrageGlobal page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ParametrageGlobal');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageGlobalPageUrlPattern);
      });

      it('edit button click should load edit ParametrageGlobal page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ParametrageGlobal');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageGlobalPageUrlPattern);
      });

      it('last delete button click should delete instance of ParametrageGlobal', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('parametrageGlobal').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageGlobalPageUrlPattern);

        parametrageGlobal = undefined;
      });
    });
  });

  describe('new ParametrageGlobal page', () => {
    beforeEach(() => {
      cy.visit(`${parametrageGlobalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ParametrageGlobal');
    });

    it('should create an instance of ParametrageGlobal', () => {
      cy.get(`[data-cy="idParamGlobal"]`).type('706239e8-a566-4177-8f38-5bebb4f68092');
      cy.get(`[data-cy="idParamGlobal"]`).should('have.value', '706239e8-a566-4177-8f38-5bebb4f68092');

      cy.get(`[data-cy="codeParam"]`).type('de manière à ce que magenta');
      cy.get(`[data-cy="codeParam"]`).should('have.value', 'de manière à ce que magenta');

      cy.get(`[data-cy="typeParam"]`).type('loin avant');
      cy.get(`[data-cy="typeParam"]`).should('have.value', 'loin avant');

      cy.get(`[data-cy="valeur"]`).type('tic-tac');
      cy.get(`[data-cy="valeur"]`).should('have.value', 'tic-tac');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        parametrageGlobal = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', parametrageGlobalPageUrlPattern);
    });
  });
});
