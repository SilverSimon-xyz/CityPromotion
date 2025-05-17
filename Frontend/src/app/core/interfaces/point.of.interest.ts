export interface PointOfInterest {
    name: string,
    description: string,
    author: string,
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
