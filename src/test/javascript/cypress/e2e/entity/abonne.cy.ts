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

describe('Abonne e2e test', () => {
  const abonnePageUrl = '/abonne';
  const abonnePageUrlPattern = new RegExp('/abonne(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const abonneSample = {};

  let abonne;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/abonnes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/abonnes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/abonnes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (abonne) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/abonnes/${abonne.idAbonne}`,
      }).then(() => {
        abonne = undefined;
      });
    }
  });

  it('Abonnes menu should load Abonnes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('abonne');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Abonne').should('exist');
    cy.url().should('match', abonnePageUrlPattern);
  });

  describe('Abonne page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(abonnePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Abonne page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/abonne/new$'));
        cy.getEntityCreateUpdateHeading('Abonne');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', abonnePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/abonnes',
          body: abonneSample,
        }).then(({ body }) => {
          abonne = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/abonnes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/abonnes?page=0&size=20>; rel="last",<http://localhost/api/abonnes?page=0&size=20>; rel="first"',
              },
              body: [abonne],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(abonnePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Abonne page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('abonne');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', abonnePageUrlPattern);
      });

      it('edit button click should load edit Abonne page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Abonne');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', abonnePageUrlPattern);
      });

      it('edit button click should load edit Abonne page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Abonne');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', abonnePageUrlPattern);
      });

      it('last delete button click should delete instance of Abonne', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('abonne').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', abonnePageUrlPattern);

        abonne = undefined;
      });
    });
  });

  describe('new Abonne page', () => {
    beforeEach(() => {
      cy.visit(`${abonnePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Abonne');
    });

    it('should create an instance of Abonne', () => {
      cy.get(`[data-cy="idAbonne"]`).type('17b53db0-d69f-409f-bc86-b64e47ab4339');
      cy.get(`[data-cy="idAbonne"]`).should('have.value', '17b53db0-d69f-409f-bc86-b64e47ab4339');

      cy.get(`[data-cy="indiceClient"]`).type('au point que');
      cy.get(`[data-cy="indiceClient"]`).should('have.value', 'au point que');

      cy.get(`[data-cy="nomAbonne"]`).type('vivre');
      cy.get(`[data-cy="nomAbonne"]`).should('have.value', 'vivre');

      cy.get(`[data-cy="prenomAbonne"]`).type('collègue');
      cy.get(`[data-cy="prenomAbonne"]`).should('have.value', 'collègue');

      cy.get(`[data-cy="telephone"]`).type('+33 622088062');
      cy.get(`[data-cy="telephone"]`).should('have.value', '+33 622088062');

      cy.get(`[data-cy="email"]`).type('Lauriane_Gaillard@yahoo.fr');
      cy.get(`[data-cy="email"]`).should('have.value', 'Lauriane_Gaillard@yahoo.fr');

      cy.get(`[data-cy="typePieceIdentite"]`).select('CNI');

      cy.get(`[data-cy="numPieceIdentite"]`).type('circulaire');
      cy.get(`[data-cy="numPieceIdentite"]`).should('have.value', 'circulaire');

      cy.get(`[data-cy="statut"]`).select('SUSPENDU');

      cy.get(`[data-cy="identifiant"]`).type('prout innombrable');
      cy.get(`[data-cy="identifiant"]`).should('have.value', 'prout innombrable');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        abonne = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', abonnePageUrlPattern);
    });
  });
});
