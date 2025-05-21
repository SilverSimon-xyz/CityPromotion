import { User } from "./user";

export interface PointOfInterest {
    id: number,
    name: string,
    description: string,
    author: User,
    latitude: number,
    longitude: number, 
    type: PointOfInterestType,
    openTime: Date, 
    closeTime: Date,
    createdAt: Date,
    updatedAt: Date
}

export enum PointOfInterestType {
    TOURISM,
    ACCOMMODATION,
    SERVICE,
    NATURE,
    OTHER
}
