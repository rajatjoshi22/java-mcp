import { Routes } from '@angular/router';

export const routes: Routes = [
    {path: 'homepage', loadChildren: () => import('./domain/domain.module').then(m => m.DomainModule)},
];
