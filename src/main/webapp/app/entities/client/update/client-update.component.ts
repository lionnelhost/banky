import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { ITypeClient } from 'app/entities/type-client/type-client.model';
import { TypeClientService } from 'app/entities/type-client/service/type-client.service';
import { ClientService } from '../service/client.service';
import { IClient } from '../client.model';
import { ClientFormGroup, ClientFormService } from './client-form.service';

@Component({
  standalone: true,
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  client: IClient | null = null;

  contratsCollection: IContrat[] = [];
  typeClientsSharedCollection: ITypeClient[] = [];

  protected clientService = inject(ClientService);
  protected clientFormService = inject(ClientFormService);
  protected contratService = inject(ContratService);
  protected typeClientService = inject(TypeClientService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ClientFormGroup = this.clientFormService.createClientFormGroup();

  compareContrat = (o1: IContrat | null, o2: IContrat | null): boolean => this.contratService.compareContrat(o1, o2);

  compareTypeClient = (o1: ITypeClient | null, o2: ITypeClient | null): boolean => this.typeClientService.compareTypeClient(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.client = client;
      if (client) {
        this.updateForm(client);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.clientFormService.getClient(this.editForm);
    if (client.idClient !== null) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(client: IClient): void {
    this.client = client;
    this.clientFormService.resetForm(this.editForm, client);

    this.contratsCollection = this.contratService.addContratToCollectionIfMissing<IContrat>(this.contratsCollection, client.contrat);
    this.typeClientsSharedCollection = this.typeClientService.addTypeClientToCollectionIfMissing<ITypeClient>(
      this.typeClientsSharedCollection,
      client.typeClient,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contratService
      .query({ filter: 'client-is-null' })
      .pipe(map((res: HttpResponse<IContrat[]>) => res.body ?? []))
      .pipe(map((contrats: IContrat[]) => this.contratService.addContratToCollectionIfMissing<IContrat>(contrats, this.client?.contrat)))
      .subscribe((contrats: IContrat[]) => (this.contratsCollection = contrats));

    this.typeClientService
      .query()
      .pipe(map((res: HttpResponse<ITypeClient[]>) => res.body ?? []))
      .pipe(
        map((typeClients: ITypeClient[]) =>
          this.typeClientService.addTypeClientToCollectionIfMissing<ITypeClient>(typeClients, this.client?.typeClient),
        ),
      )
      .subscribe((typeClients: ITypeClient[]) => (this.typeClientsSharedCollection = typeClients));
  }
}
