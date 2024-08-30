import { Routes } from '@angular/router';
import { LayoutComponent } from './pages/layout/layout.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AuthComponent } from './pages/auth/auth.component';


export const routes: Routes = [
    {path:'',component: LayoutComponent, children:[
            {
                path: 'dashboard',
                component: DashboardComponent
            }
        ]
    },
    {path: 'auth', component: AuthComponent, title: 'Auth'}
];
