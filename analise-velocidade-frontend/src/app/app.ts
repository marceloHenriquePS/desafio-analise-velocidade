import { Component, computed, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { AnalyzeService } from './services/analyze.service';
import { AnalyzeResponse, GravityType } from './models/analyze.model';

@Component({
  selector: 'app-root',
  imports: [DatePipe],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  private analyzeService = inject(AnalyzeService);

  selectedFile = signal<File | null>(null);
  loading = signal(false);
  errorMessage = signal('');
  result = signal<AnalyzeResponse | null>(null);
  gravityFilter = signal<GravityType | 'ALL'>('ALL');

  filteredEpisodes = computed(() => {
    const episodes = this.result()?.episodes ?? [];
    const filter = this.gravityFilter();
    return filter === 'ALL' ? episodes : episodes.filter(e => e.gravityType === filter);
  });

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    this.selectedFile.set(input.files?.[0] ?? null);
    this.errorMessage.set('');
    this.result.set(null);
    this.gravityFilter.set('ALL');
  }

  onGravityChange(event: Event) {
    const select = event.target as HTMLSelectElement;
    this.gravityFilter.set(select.value as GravityType | 'ALL');
  }

  analyze() {
    const file = this.selectedFile();
    if (!file) return;

    this.loading.set(true);
    this.errorMessage.set('');
    this.analyzeService.analyze(file).subscribe({
      next: response => {
        this.result.set(response);
        this.loading.set(false);
      },
      error: err => {
        this.errorMessage.set(err.error?.message ?? err.error ?? 'Erro ao enviar o arquivo');
        this.loading.set(false);
      }
    });
  }
}