export interface Contest {
    name: string,
    description: string,
    author: string,
    rules: string,
    goal: string,
    prize: string,
    deadLine: Date,
    active: boolean
    createdAt: Date,
    updatedAt: Date,
    numberOfParticipant: number
}
