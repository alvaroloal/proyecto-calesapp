export interface UsuarioEdit {
    username: string;
    email: string;
    password?: string;
    rol: 'USER' | 'ADMIN';
}
