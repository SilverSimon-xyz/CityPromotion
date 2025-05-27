import { Contest } from "./contest"
import { MediaFile } from "./media.file"
import { User } from "./user"

export interface ContestParticipant {
    id: number
    participant: User,
    contest: Contest,
    mediaFile?: MediaFile, 
    quoteCriterion: QuoteCriterion
}

export interface QuoteCriterion {
    vote: number,
    description: string,
    isQuote: boolean
}
