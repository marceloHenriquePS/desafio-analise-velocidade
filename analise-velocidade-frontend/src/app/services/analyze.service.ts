import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { AnalyzeResponse } from "../models/analyze.model";

@Injectable({ providedIn: 'root'})
export class AnalyzeService {
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8080/api/v1'

    analyze(file: File) {
        const formData = new FormData();
        formData.append('file', file);
        return this.http.post<AnalyzeResponse>(`${this.apiUrl}/analyze`, formData);
    }
}