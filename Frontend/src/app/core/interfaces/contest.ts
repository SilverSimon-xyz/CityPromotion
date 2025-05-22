import { User } from "./user"

export interface Contest {
    id: number,
    name: string,
    description: string,
    author: User,
    rules: string,
    goal: string,
    prize: string,
    deadline: Date,
    active: boolean
    createdAt: Date,
    updatedAt: Date,
    numberOfParticipant: number
}
