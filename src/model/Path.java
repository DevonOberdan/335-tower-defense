package model;

import java.awt.Point;

public abstract class Path {
		private boolean L, R, U, D;
		
		public Path() {
			L = false;
			R = false;
			U = false;
			D = false;
		}
		
		public boolean getL() {
			return this.L;
		}
		public boolean getR() {
			return this.R;
		}
		public boolean getU() {
			return this.U;
		}
		public boolean getD() {
			return this.D;
		}
		
		public void setL(boolean a) {
			this.L = a;
		}
		public void setR(boolean a) {
			this.R = a;
		}
		public void setU(boolean a) {
			this.U = a;
		}
		public void setD(boolean a) {
			this.D = a;
		}
		

		public abstract Point checkTurns(Point p, Enemy e);
}
