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

describe('Agence e2e test', () => {
  const agencePageUrl = '/agence';
  const agencePageUrlPattern = new RegExp('/agence(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const agenceSample = {};

  let agence;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/agences+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/agences').as('postEntityRequest');
    cy.intercept('DELETE', '/api/agences/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (agence) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/agences/${agence.idAgence}`,
      }).then(() => {
        agence = undefined;
      });
    }
  });

  it('Agences menu should load Agences page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('agence');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Agence').should('exist');
    cy.url().should('match', agencePageUrlPattern);
  });

  describe('Agence page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(agencePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Agence page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/agence/new$'));
        cy.getEntityCreateUpdateHeading('Agence');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', agencePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/agences',
          body: agenceSample,
        }).then(({ body }) => {
          agence = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/agences+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/agences?page=0&size=20>; rel="last",<http://localhost/api/agences?page=0&size=20>; rel="first"',
              },
              body: [agence],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(agencePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Agence page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('agence');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', agencePageUrlPattern);
      });

      it('edit button click should load edit Agence page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Agence');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', agencePageUrlPattern);
      });

      it('edit button click should load edit Agence page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Agence');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', agencePageUrlPattern);
      });

      it('last delete button click should delete instance of Agence', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('agence').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', agencePageUrlPattern);

        agence = undefined;
      });
    });
  });

  describe('new Agence page', () => {
    beforeEach(() => {
      cy.visit(`${agencePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Agence');
    });

    it('should create an instance of Agence', () => {
      cy.get(`[data-cy="idAgence"]`).type('2a82d156-8fc8-40b1-bf50-2cd80163a200');
      cy.get(`[data-cy="idAgence"]`).should('have.value', '2a82d156-8fc8-40b1-bf50-2cd80163a200');

      cy.get(`[data-cy="codeAgence"]`).type('aigre');
      cy.get(`[data-cy="codeAgence"]`).should('have.value', 'aigre');

      cy.get(`[data-cy="nomAgence"]`).type('en face de vaste commissionnaire');
      cy.get(`[data-cy="nomAgence"]`).should('have.value', 'en face de vaste commissionnaire');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        agence = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', agencePageUrlPattern);
    });
  });
});
