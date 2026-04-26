import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CodeAnalyzerComponent } from './code-analyzer/code-analyzer.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { DomainService } from './domain.service';


const routes = [
  {path: '', component: CodeAnalyzerComponent}
];
@NgModule({
  declarations: [CodeAnalyzerComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes)
  ],
  providers: [DomainService]
})
export class DomainModule { }
