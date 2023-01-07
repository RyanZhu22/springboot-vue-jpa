import { defineStore } from "pinia";

export const useStore = defineStore("main", () => {
	const count = ref(0);
	function increment() {
		count.value++;	
	}

	return { count, increment };
});
