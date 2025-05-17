import { MediaFile } from "./media.file"

export interface ContestParticipation {
    participant: string,
    contestName: string,
    mediaFile: MediaFile, 
    quoteCriterion: QuoteCritirion
}

export interface QuoteCritirion {
    vote: number,
    description: string,
    isQuoted: boolean
}
