import { Component } from '@angular/core';
import { DomainService } from '../domain.service';

@Component({
  selector: 'app-code-analyzer',
  templateUrl: './code-analyzer.component.html',
  styleUrl: './code-analyzer.component.scss'
})
export class CodeAnalyzerComponent {
  constructor(public  _fsdService: DomainService) {}
 selectedFiles: File[] = [];
  prompt = '';
  isGenerating = false; // Added for UI feedback
  copied = false; // State to show "Copied" feedback

  onFileSelected(event: any) {
    this.selectedFiles = Array.from(event.target.files);
  }

  async submit() {
    if (this.selectedFiles.length === 0) return;
    
    this.isGenerating = true;
    try {
      await this._fsdService.generateCode(this.selectedFiles, this.prompt);
    } finally {
      this.isGenerating = false; // Turn off loading state
    }
  }

  copyToClipboard() {
  const code = this._fsdService.generatedCode();
  if (code) {
    navigator.clipboard.writeText(code).then(() => {
      this.copied = true;
      // Reset the "Copied" text after 2 seconds
      setTimeout(() => this.copied = false, 2000);
    });
  }
}

}
