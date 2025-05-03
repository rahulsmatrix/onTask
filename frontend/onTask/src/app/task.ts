export class Task {

    constructor(
        public id: number,
        public description: string,
        public targetDate: Date,
        public status: boolean
      ) { 
    
        }
}
