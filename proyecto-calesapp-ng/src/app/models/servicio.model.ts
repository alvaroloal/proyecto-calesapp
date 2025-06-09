export interface Servicio {
    id: number;
    tipoServicio: string;
    tarifa: number;
    duracion: number;
    descripcion: string;
    disponibilidad: boolean;
}