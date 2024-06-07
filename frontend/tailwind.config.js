import daisyui from 'daisyui';

export default {
  plugins: [daisyui],
  theme: {
    extend: {},
  },
  content: ["./index.html",'./src/**/*.{svelte,js,ts}'],
  daisyui: {
    themes: ["light", "dark", "cupcake", 'wireframe'],
  },
}