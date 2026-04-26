import { Injectable,signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DomainService {

  constructor() { }
  // Use signals for easy UI binding in Angular 18
 generatedCode = signal<string>('');

  async generateCode(files: File[], prompt: string) {
    this.generatedCode.set(''); 
    
    const base64Files = await Promise.all(files.map(f => this.toBase64(f)));

    const response = await fetch('http://localhost:9099/generate', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ base64Content: base64Files, userPrompt: prompt })
    });

    const reader = response.body?.getReader();
    const decoder = new TextDecoder();

    if (reader) {
      while (true) {
        const { done, value } = await reader.read();
        if (done) break;
        
        const chunk = decoder.decode(value);
        // If your Spring Flux uses Server-Sent Events, you might need to clean the "data:" prefix
        const cleanChunk = chunk.replace(/^data: ?/gm, ''); 
        this.generatedCode.update(prev => prev + cleanChunk);
      }
    }
  }

  private toBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve((reader.result as string).split(',')[1]);
      reader.onerror = error => reject(error);
    });
  }
}
