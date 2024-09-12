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

describe('ContratAbonnement e2e test', () => {
  const contratAbonnementPageUrl = '/contrat-abonnement';
  const contratAbonnementPageUrlPattern = new RegExp('/contrat-abonnement(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const contratAbonnementSample = {};

  let contratAbonnement;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/contrat-abonnements+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/contrat-abonnements').as('postEntityRequest');
    cy.intercept('DELETE', '/api/contrat-abonnements/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (contratAbonnement) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/contrat-abonnements/${contratAbonnement.id}`,
      }).then(() => {
        contratAbonnement = undefined;
      });
    }
  });

  it('ContratAbonnements menu should load ContratAbonnements page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('contrat-abonnement');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ContratAbonnement').should('exist');
    cy.url().should('match', contratAbonnementPageUrlPattern);
  });

  describe('ContratAbonnement page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(contratAbonnementPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ContratAbonnement page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/contrat-abonnement/new$'));
        cy.getEntityCreateUpdateHeading('ContratAbonnement');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/contrat-abonnements',
          body: contratAbonnementSample,
        }).then(({ body }) => {
          contratAbonnement = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/contrat-abonnements+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/contrat-abonnements?page=0&size=20>; rel="last",<http://localhost/api/contrat-abonnements?page=0&size=20>; rel="first"',
              },
              body: [contratAbonnement],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(contratAbonnementPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ContratAbonnement page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('contratAbonnement');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementPageUrlPattern);
      });

      it('edit button click should load edit ContratAbonnement page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ContratAbonnement');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementPageUrlPattern);
      });

      it('edit button click should load edit ContratAbonnement page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ContratAbonnement');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementPageUrlPattern);
      });

      it('last delete button click should delete instance of ContratAbonnement', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('contratAbonnement').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementPageUrlPattern);

        contratAbonnement = undefined;
      });
    });
  });

  describe('new ContratAbonnement page', () => {
    beforeEach(() => {
      cy.visit(`${contratAbonnementPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ContratAbonnement');
    });

    it('should create an instance of ContratAbonnement', () => {
      cy.get(`[data-cy="idContrat"]`).type('en face de ainsi');
      cy.get(`[data-cy="idContrat"]`).should('have.value', 'en face de ainsi');

      cy.get(`[data-cy="idAbonne"]`).type('au-dessus de en vérité au-dedans de');
      cy.get(`[data-cy="idAbonne"]`).should('have.value', 'au-dessus de en vérité au-dedans de');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        contratAbonnement = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', contratAbonnementPageUrlPattern);
    });
  });
});
