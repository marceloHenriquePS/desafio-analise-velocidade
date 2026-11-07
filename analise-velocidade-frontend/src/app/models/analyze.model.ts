export type GravityType = 'LIGHT' | 'SERIOUS' | 'VERY_SERIOUS';

export interface Episode {
    plate: string;
    start: string;
    end: string;
    duration: number;
    maxVelocity: number;
    maxExcess: number;
    gravityType: GravityType;
}

export interface Rankings {
    plate: string;
    totalPoints: number;
    maxVelocity: number;
}

export interface InvalidLine {
    lineNumber: number;
    cause: string;
}

export interface Summary {
    readLines: number;
    validLines: number;
    totalEpisodes: number;
}

export interface AnalyzeResponse {
    episodes: Episode[];
    rankings: Rankings[];
    invalidLines: InvalidLine[];
    summary: Summary;
}