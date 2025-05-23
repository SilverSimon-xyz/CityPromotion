import { Contest } from "./contest"
import { MediaFile } from "./media.file"
import { User } from "./user"

export interface ContestParticipation {
    id: number
    participant: User,
    contest: Contest,
    mediaFile?: MediaFile, 
    quoteCriterion: QuoteCritirion
}

export interface QuoteCritirion {
    vote: number,
    description: string,
    isQuoted: boolean
}
