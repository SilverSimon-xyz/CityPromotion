import { Role } from "./role";

export interface User {
    id: number,
    firstname: string, 
    lastname: string,
    email: string, 
    password: string, 
    role: Role,
    createdAt: Date, 
    updatedAt: Date
}