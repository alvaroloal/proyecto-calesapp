export interface Usuario {
    id: string;
    username: string;
    email: string;
    password?: string;
    rol: 'ROLE_USER' | 'ROLE_ADMIN';
}